package org.example.domain;

import org.example.util.Objectz;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Collectors;

public record Client(
        String ipAddress,
        Location location,
        String isp,
        double ispRating,
        IsoAlpha2CountryCode countryCode
) {

    public Client {
        Objects.requireNonNull(ipAddress);
        Objects.requireNonNull(location);
        Objects.requireNonNull(isp);
        Objectz.require(Double.isFinite(ispRating));
        Objectz.require(ispRating >= 0d);
        Objects.requireNonNull(countryCode);
    }

    public Map<Distance, Server> closestServers(List<Server> servers, int limit) {
        Objects.requireNonNull(servers);
        Objectz.require(limit > 0);
        Map<Distance, Server> closestServers = servers.stream()
                .collect(Collectors.toMap(
                        server -> location.distance(server.location()),
                        server -> server,
                        (server1, server2) -> server1,
                        TreeMap::new));
        return closestServers.entrySet().stream()
                .limit(limit)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

}
