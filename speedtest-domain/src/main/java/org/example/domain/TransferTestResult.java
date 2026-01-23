package org.example.domain;

public record TransferTestResult(
        Double rateInMbps,
        Integer bytes,
        Long durationInMs
) {

}
