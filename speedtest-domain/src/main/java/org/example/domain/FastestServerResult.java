package org.example.domain;

public record FastestServerResult(
        Server server,
        LatencyTestResult latencyTestResult
) {

}
