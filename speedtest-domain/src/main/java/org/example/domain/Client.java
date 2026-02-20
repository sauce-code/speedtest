package org.example.domain;

import org.example.domain.location.Distance;
import org.example.domain.location.Location;

import java.util.List;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Collectors;

public record Client(
        String ipAddress,
        Location location,
        String isp,
        ISPRating ispRating,
        IsoAlpha2CountryCode countryCode
) {

    public Client {
        Objects.requireNonNull(ipAddress);
        Objects.requireNonNull(location);
        Objects.requireNonNull(isp);
        Objects.requireNonNull(ispRating);
        Objects.requireNonNull(countryCode);
    }

    public TreeMap<Distance, Server> closestServers(List<Server> servers) {
        Objects.requireNonNull(servers);
        return servers.stream()
                .collect(Collectors.toMap(
                        server -> location.distance(server.location()),
                        server -> server,
                        (server1, server2) -> server1,
                        TreeMap::new));
    }

}
