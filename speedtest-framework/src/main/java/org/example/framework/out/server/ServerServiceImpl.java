package org.example.framework.out.server;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.example.application.out.ServerService;
import org.example.domain.Server;
import org.example.framework.out.config.ParsingException;
import org.example.framework.out.http.HttpGetClient;
import org.example.framework.out.http.ServerRequestException;
import org.example.framework.out.xml.Context;
import org.example.util.Objectz;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class ServerServiceImpl implements ServerService {

    private final HttpGetClient httpGetClient;
    private final Context context;

    @Inject
    public ServerServiceImpl(
            HttpGetClient httpGetClient,
            Context context) {
        this.httpGetClient = httpGetClient;
        this.context = context;
    }

    private static final Set<URI> SERVER_URLS = Set.of(
            URI.create("https://www.speedtest.net/speedtest-servers-static.php"),
            URI.create("http://c.speedtest.net/speedtest-servers-static.php"),
            URI.create("https://www.speedtest.net/speedtest-servers.php"),
            URI.create("http://c.speedtest.net/speedtest-servers.php"));

    @Override
    public List<Server> servers(int threadsPerUrl) {
        Objectz.require(threadsPerUrl > 0);
        return SERVER_URLS.stream()
                .map(base -> {
                    try {
                        String s = "%s?threads=%d".formatted(base, threadsPerUrl);
                        URI uri = URI.create(s);
                        byte[] bytes = httpGetClient.get(uri);
                        return getServersFromXml(bytes);
                    } catch (ParsingException | ServerRequestException e) {
                        return Collections.<Server>emptyList();
                    }
                })
                .flatMap(Collection::stream)
                .toList();
    }

    private List<Server> getServersFromXml(byte[] bytes) {
        Objects.requireNonNull(bytes);
        try (InputStream is = new ByteArrayInputStream(bytes)) {
            ServerSetting serverSetting = context.unmarshal(is, ServerSetting.class);
            return serverSetting.getServers().getServerList().stream()
                    .map(org.example.framework.out.server.Server::toDomain)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList();
        } catch (IOException | JAXBException e) {
            throw new ParsingException(e);
        }
    }

}
