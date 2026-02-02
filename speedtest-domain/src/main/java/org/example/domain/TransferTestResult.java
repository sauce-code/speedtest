package org.example.domain;

import org.example.util.Objectz;

public record TransferTestResult(
        double rateInMbps,
        int bytes,
        long durationInMs
) {

    public TransferTestResult {
        Objectz.require(Double.isFinite(rateInMbps));
        Objectz.require(rateInMbps >= 0.0);
        Objectz.require(bytes >= 0);
        Objectz.require(durationInMs >= 0L);
    }

}
