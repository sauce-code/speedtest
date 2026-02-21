package org.example.application.out;

import org.example.domain.LatencyTestResultFixture;
import org.example.domain.ServerFixture;

public class ServerLatencyResultFixture {

    public static ServerLatencyResult some() {
        return new ServerLatencyResult(
                ServerFixture.some().get(25),
                LatencyTestResultFixture.some());
    }

}
