package org.example.application.out;

import org.example.domain.LatencyTestResult;
import org.example.domain.Server;

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
