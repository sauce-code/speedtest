package org.example.domain;

import org.example.domain.location.Distance;

import java.util.Objects;

public record LatencyTestResult(
        Latency latency,
        Distance distance
) {

    public LatencyTestResult {
        Objects.requireNonNull(latency);
        Objects.requireNonNull(distance);
    }

}
