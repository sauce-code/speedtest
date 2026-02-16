package org.example.domain;

import org.example.util.Objectz;

public record TransferTestResult(
        int bytes,
        long durationInMs
) {

    public TransferTestResult {
        Objectz.require(bytes >= 0);
        Objectz.require(durationInMs >= 0L);
    }

    public double rateInMbps() {
        return (bytes * 8.0) / (durationInMs * 1000.0);
    }

}
