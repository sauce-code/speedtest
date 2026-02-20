package org.example.domain;

import org.example.domain.location.Distance;

import java.util.List;

public class ServerDistanceResultFixture {

    public static List<ServerDistanceResult> some() {
        var servers = ServerFixture.some();
        return List.of(
                new ServerDistanceResult(servers.get(0), Distance.valueOf("155.245077")),
                new ServerDistanceResult(servers.get(1), Distance.valueOf("167.028964")),
                new ServerDistanceResult(servers.get(2), Distance.valueOf("167.028964")));
    }

}
