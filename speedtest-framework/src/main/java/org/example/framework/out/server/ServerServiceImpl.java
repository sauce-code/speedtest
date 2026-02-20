package org.example.framework.out.server;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.application.out.ServerService;
import org.example.domain.Server;
import org.example.domain.location.Distance;
import org.example.framework.out.http.HttpGetClient;
import org.example.framework.out.http.ServerRequestException;
import org.example.framework.out.server.model.Settings;
import org.example.framework.out.xml.Context;
import org.example.util.Objectz;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class ServerServiceImpl implements ServerService {

    private final Properties properties;
    private final HttpGetClient httpGetClient;
    private final Context context;

    public ServerServiceImpl(
            Properties properties,
            HttpGetClient httpGetClient,
            Context context) {
        this.properties = properties;
        this.httpGetClient = httpGetClient;
        this.context = context;
    }

    @Override
    public List<Server> servers(int threadsPerUrl) {
        Objectz.require(threadsPerUrl > 0);
        return properties.baseUri().stream()
                .map(base -> {
                    try {
                        String s = "%s?threads=%d".formatted(base, threadsPerUrl);
                        URI uri = URI.create(s);
                        byte[] bytes = httpGetClient.get(uri);
                        return getServersFromXml(bytes);
                    } catch (ParsingException | ServerRequestException e) {
                        throw new ServerServiceException(e);
                    }
                })
                .flatMap(Collection::stream)
                .distinct()
                .toList();
    }

    private List<Server> getServersFromXml(byte[] bytes) throws ParsingException {
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

    @Override
    public Map<Distance, Server> limit(SortedMap<Distance, Server> treeMap) {
        Objects.requireNonNull(treeMap);
        return treeMap.entrySet().stream()
                .limit(properties.limit())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

}
