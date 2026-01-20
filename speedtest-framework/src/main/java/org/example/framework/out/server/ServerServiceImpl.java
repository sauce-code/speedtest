package org.example.framework.out.server;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.example.application.out.ServerService;
import org.example.application.out.model.DistanceUnit;
import org.example.domain.Server;
import org.example.framework.out.config.MissingResultException;
import org.example.framework.out.config.ParsingException;
import org.example.framework.out.http.HttpGetClient;
import org.example.framework.out.http.ServerRequestException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

public class ServerServiceImpl implements ServerService {

    private final HttpGetClient httpGetClient;

    public ServerServiceImpl(HttpGetClient httpGetClient) {
        this.httpGetClient = httpGetClient;
    }

    private static final Set<String> SERVER_URLS = new HashSet<>(Arrays.asList(
            "https://www.speedtest.net/speedtest-servers-static.php", "http://c.speedtest.net/speedtest-servers-static.php",
            "https://www.speedtest.net/speedtest-servers.php", "http://c.speedtest.net/speedtest-servers.php"));

    @Override
    public List<Server> servers(int threadsPerUrl) {
        if (threadsPerUrl > 0) {
            List<Server> servers = SERVER_URLS.stream()
                    .map(url -> {
                        try {
                            final byte[] bytes = httpGetClient.get(String.format("%s?threads=%d", url, threadsPerUrl));
                            return getServersFromXml(bytes);
                        } catch (ParsingException | MissingResultException | ServerRequestException e) {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
            if (!servers.isEmpty()) {
                return servers;
            } else {
                throw new MissingResultException("Empty server list");
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    private List<Server> getServersFromXml(byte[] bytes) throws ParsingException, MissingResultException {
        if (bytes != null) {
            try (InputStream is = new ByteArrayInputStream(bytes)) {
                JAXBContext jaxbContext = JAXBContext.newInstance(ServerSetting.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                ServerSetting serverSetting = (ServerSetting) jaxbUnmarshaller.unmarshal(is);
                if (serverSetting != null && serverSetting.servers() != null) {
                    return serverSetting.servers();
                } else {
                    throw new MissingResultException("Missing server list result");
                }
            } catch (IOException | JAXBException e) {
                throw new ParsingException(e);
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Map<Double, Server> findClosestServers(double lat, double lon, int limit, DistanceUnit distanceUnit, List<Server> serverList) {
        if (limit > 0 && distanceUnit != null && serverList != null && !serverList.isEmpty()) {
            Map<Double, Server> closestServers = serverList.stream()
                    .collect(Collectors.toMap(
                            server -> {
                                try {
                                    return calculateDistance(lat, lon, server.lat(), server.lon(), distanceUnit);
                                } catch (UnsupportedUnitException e) {
                                    throw new RuntimeException(e);
                                }
                            },
                            server -> server, (server1, server2) -> server1, TreeMap::new));
            final Map<Double, Server> limitedClosestServers = closestServers.entrySet().stream()
                    .limit(limit)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            if (!limitedClosestServers.isEmpty()) {
                return limitedClosestServers;
            } else {
                throw new MissingResultException("Empty list for limited closest servers");
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    public static double calculateDistance(final double lat1, final double lon1, final double lat2, final double lon2, final DistanceUnit distanceUnit)
            throws UnsupportedUnitException {
        if (distanceUnit != null) {
            if (lat1 == lat2 && lon1 == lon2) {
                return 0d;
            } else {
                final double theta = lon1 - lon2;
                double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2))
                        + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
                dist = Math.acos(dist);
                dist = Math.toDegrees(dist);
                dist = dist * 60 * 1.1515; // miles
                return switch (distanceUnit) {
                    case MILE -> dist;
                    case KILOMETER -> dist * 1.609344;
                    case NAUTICAL_MILE -> dist * 0.8684;
                };
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

}
