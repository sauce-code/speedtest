package org.example.domain;

public class TransferTestResultFixture {

    public static TransferTestResult download() {
        return new TransferTestResult(
                118_464_432,
                10_043L);
    }

    public static TransferTestResult upload() {
        return new TransferTestResult(
                114_284_544,
                9_726L);
    }

}
