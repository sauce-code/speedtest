package org.example.domain;

import org.example.domain.location.Distance;

import java.util.List;

public class ServerDistanceFixture {

    public static List<ServerDistance> some() {
        var servers = ServerFixture.some();
        return List.of(
                new ServerDistance(servers.get(0), Distance.valueOf("574.754903")),
                new ServerDistance(servers.get(1), Distance.valueOf("574.754903")),
                new ServerDistance(servers.get(2), Distance.valueOf("594.372700")),
                new ServerDistance(servers.get(3), Distance.valueOf("651.523560")),
                new ServerDistance(servers.get(4), Distance.valueOf("570.406698")),
                new ServerDistance(servers.get(5), Distance.valueOf("570.406698")),
                new ServerDistance(servers.get(6), Distance.valueOf("570.406698")),
                new ServerDistance(servers.get(7), Distance.valueOf("570.406698")),
                new ServerDistance(servers.get(8), Distance.valueOf("570.406698")),
                new ServerDistance(servers.get(9), Distance.valueOf("570.406698")),
                new ServerDistance(servers.get(10), Distance.valueOf("177.641340")),
                new ServerDistance(servers.get(11), Distance.valueOf("177.729048")),
                new ServerDistance(servers.get(12), Distance.valueOf("275.126068")),
                new ServerDistance(servers.get(13), Distance.valueOf("119.001290")),
                new ServerDistance(servers.get(14), Distance.valueOf("119.085796")),
                new ServerDistance(servers.get(15), Distance.valueOf("119.085796")),
                new ServerDistance(servers.get(16), Distance.valueOf("119.085796")),
                new ServerDistance(servers.get(17), Distance.valueOf("119.085796")),
                new ServerDistance(servers.get(18), Distance.valueOf("119.085796")),
                new ServerDistance(servers.get(19), Distance.valueOf("119.085796")),
                new ServerDistance(servers.get(20), Distance.valueOf("36.478542")),
                new ServerDistance(servers.get(21), Distance.valueOf("45.317566")),
                new ServerDistance(servers.get(22), Distance.valueOf("87.310634")),
                new ServerDistance(servers.get(23), Distance.valueOf("87.310634")),
                new ServerDistance(servers.get(24), Distance.valueOf("119.085796")),
                new ServerDistance(servers.get(25), Distance.valueOf("119.085796")),
                new ServerDistance(servers.get(26), Distance.valueOf("119.085796")),
                new ServerDistance(servers.get(27), Distance.valueOf("119.085796")),
                new ServerDistance(servers.get(28), Distance.valueOf("119.085796")),
                new ServerDistance(servers.get(29), Distance.valueOf("119.085796")));
    }

}
