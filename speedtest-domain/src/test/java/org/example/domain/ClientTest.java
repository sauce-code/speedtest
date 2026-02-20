package org.example.domain;

import org.example.domain.location.Distance;
import org.junit.jupiter.api.Test;

import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClientTest {

    @Test
    void givenServers_whenClosestServers_thenResult() {
        var client = ClientFixture.some();
        var servers = ServerFixture.some();
        var actual = client.closestServers(servers);
        var expected = new TreeMap<Distance, Server>();
        expected.put(
                Distance.valueOf("155.245077"),
                servers.get(0));
        expected.put(
                Distance.valueOf("167.028964"),
                servers.get(1));
        assertEquals(expected, actual);
    }

}
