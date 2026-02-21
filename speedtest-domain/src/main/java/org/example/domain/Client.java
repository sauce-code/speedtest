package org.example.domain;

import org.example.domain.location.Location;

import java.util.List;
import java.util.Objects;

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

    public List<ServerDistance> serverDistances(List<Server> servers) {
        Objects.requireNonNull(servers);
        return servers.stream()
                .map(server -> new ServerDistance(server, location.distance(server.location())))
                .toList();
    }

}
