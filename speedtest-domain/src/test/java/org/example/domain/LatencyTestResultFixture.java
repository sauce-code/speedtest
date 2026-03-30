package org.example.domain;

public class LatencyTestResultFixture {

    public static LatencyTestResult some() {
        return new LatencyTestResult(
                Latency.valueOf("3.60"),
                ServerDistanceFixture.some().get(25).distance());
    }

}
