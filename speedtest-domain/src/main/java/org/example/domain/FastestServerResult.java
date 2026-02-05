package org.example.domain;

import java.util.Objects;

public record FastestServerResult(
        Server server,
        LatencyTestResult latencyTestResult
) {

    public FastestServerResult {
        Objects.requireNonNull(server);
        Objects.requireNonNull(latencyTestResult);
    }

}
