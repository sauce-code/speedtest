package org.example.domain;

import org.example.domain.location.Distance;

import java.util.Objects;

public record ServerDistance(
        Server server,
        Distance distance
) {

    public ServerDistance {
        Objects.requireNonNull(server);
        Objects.requireNonNull(distance);
    }

}
