package org.example.domain;

import org.example.domain.location.Distance;

import java.util.Objects;

public record ServerDistanceResult(
        Server server,
        Distance distance
) {

    public ServerDistanceResult {
        Objects.requireNonNull(server);
        Objects.requireNonNull(distance);
    }

}
