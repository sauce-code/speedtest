package org.example.domain;

import java.util.Objects;

public record ServerLatencyResult(
        Server server,
        LatencyTestResult latencyTestResult
) {

    public ServerLatencyResult {
        Objects.requireNonNull(server);
        Objects.requireNonNull(latencyTestResult);
    }

}
