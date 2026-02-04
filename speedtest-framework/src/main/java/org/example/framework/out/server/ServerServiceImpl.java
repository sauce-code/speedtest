package org.example.framework.out.server;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.example.application.out.ServerService;
import org.example.domain.Server;
import org.example.framework.out.config.ParsingException;
import org.example.framework.out.http.HttpGetClient;
import org.example.framework.out.http.ServerRequestException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
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
                .map(urlString -> {
                    try {
                        String s = "%s?threads=%d".formatted(urlString, threadsPerUrl);
                        URI uri = URI.create(s);
                        byte[] bytes = httpGetClient.get(uri);
                        return getServersFromXml(bytes);
                    } catch (ParsingException | ServerRequestException e) {
                        return Collections.<Server>emptyList();
                    }
                })
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
                    .map(org.example.framework.out.server.Server::toDomain)
                    .toList();
        } catch (IOException | JAXBException e) {
            throw new ParsingException(e);
        }
    }

}
