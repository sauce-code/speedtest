package org.example.framework.out.server;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.example.application.out.ServerService;
import org.example.domain.DistanceUnit;
import org.example.domain.Server;
import org.example.framework.out.config.MissingResultException;
import org.example.framework.out.config.ParsingException;
import org.example.framework.out.http.HttpGetClient;
import org.example.framework.out.http.ServerRequestException;
import org.example.util.Objectz;

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
        if (threadsPerUrl <= 0) {
            throw new IllegalArgumentException();
        }
        return SERVER_URLS.stream()
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
    }

    private List<Server> getServersFromXml(byte[] bytes) {
        Objects.requireNonNull(bytes);
        try (InputStream is = new ByteArrayInputStream(bytes)) {
            JAXBContext jaxbContext = JAXBContext.newInstance(ServerSetting.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            ServerSetting serverSetting = (ServerSetting) jaxbUnmarshaller.unmarshal(is);
            return serverSetting.getServers().getServerList().stream()
                    .map(s -> new Server(
                            s.getUrl(),
                            s.getLat(),
                            s.getLon(),
                            s.getCity(),
                            s.getCountry(),
                            s.getIsoAlpha2CountryCode(),
                            s.getSponsor(),
                            s.getId(),
                            s.getHost()))
                    .toList();
        } catch (IOException | JAXBException e) {
            throw new ParsingException(e);
        }
    }

    @Override
    public Map<Double, Server> findClosestServers(double lat, double lon, int limit, DistanceUnit distanceUnit, List<Server> serverList) {
        Objects.requireNonNull(distanceUnit);
        Objects.requireNonNull(serverList);
        Objectz.require(limit > 0);
        Map<Double, Server> closestServers = serverList.stream()
                .collect(Collectors.toMap(
                        server -> calculateDistance(lat, lon, server.lat(), server.lon(), distanceUnit),
                        server -> server, (server1, server2) -> server1, TreeMap::new));
        return closestServers.entrySet().stream()
                .limit(limit)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2, DistanceUnit distanceUnit) {
        Objects.requireNonNull(distanceUnit);
        if (lat1 == lat2 && lon1 == lon2) {
            return 0d;
        }
        double theta = lon1 - lon2;
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

}
