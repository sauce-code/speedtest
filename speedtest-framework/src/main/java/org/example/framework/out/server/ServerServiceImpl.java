package org.example.framework.out.server;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.application.out.Logger;
import org.example.application.out.ServerService;
import org.example.domain.Server;
import org.example.framework.out.http.HttpGetClient;
import org.example.framework.out.http.ServerRequestException;
import org.example.framework.out.server.model.Settings;
import org.example.framework.out.xml.Context;
import org.example.util.Objectz;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class ServerServiceImpl implements ServerService {

    private final Logger logger;
    private final Properties properties;
    private final HttpGetClient httpGetClient;
    private final Context context;

    public ServerServiceImpl(
            Logger logger,
            Properties properties,
            HttpGetClient httpGetClient,
            Context context) {
        this.logger = logger;
        this.properties = properties;
        this.httpGetClient = httpGetClient;
        this.context = context;
    }

    @Override
    public List<Server> servers(int threadsPerUrl) {
        Objectz.require(threadsPerUrl > 0);
        return properties.baseUri().stream()
                .map(base -> "%s?threads=%d".formatted(base, threadsPerUrl))
                .map(URI::create)
                .map(this::serversFromURI)
                .flatMap(Collection::stream)
                .distinct()
                .toList();
    }

    private List<Server> serversFromURI(URI uri) {
        try {
            byte[] bytes = httpGetClient.get(uri);
            return serversFromXml(bytes);
        } catch (ParsingException | ServerRequestException e) {
            logger.warnv("Could not receive any servers for {0}: {1}", uri, e.getMessage());
            return Collections.emptyList();
        }
    }

    private List<Server> serversFromXml(byte[] bytes) throws ParsingException {
        Objects.requireNonNull(bytes);
        try (InputStream is = new ByteArrayInputStream(bytes)) {
            Settings settings = context.unmarshal(is, Settings.class);
            return settings.servers.server.stream()
                    .map(org.example.framework.out.server.model.Server::toDomain)
                    .toList();
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

}
