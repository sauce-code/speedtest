package org.example.domain;

import java.time.Instant;

public class SpeedtestResultFixture {

    public static SpeedtestResult some() {
        return new SpeedtestResult(
                SpeedtestResultIDFixture.some(),
                Instant.ofEpochSecond(1_774_612_380L, 3_936_600L),
                Instant.ofEpochSecond(1_774_612_403L, 669_116_800L),
                ClientFixture.some(),
                ServerFixture.some().get(25),
                LatencyTestResultFixture.some(),
                TransferTestResultFixture.download(),
                TransferTestResultFixture.upload(),
                ShareURLFixture.some());
    }

}
