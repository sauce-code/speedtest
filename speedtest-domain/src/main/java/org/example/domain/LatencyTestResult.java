package org.example.domain;

import org.example.domain.location.Distance;
import org.example.util.Objectz;

import java.util.Objects;

public record LatencyTestResult(
        double latency,
        Distance distance
) {

    public LatencyTestResult {
        Objectz.require(Double.isFinite(latency));
        Objectz.require(latency >= 0d);
        Objects.requireNonNull(distance);
    }

}
