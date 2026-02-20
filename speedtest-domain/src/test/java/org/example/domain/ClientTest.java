package org.example.domain;

import org.example.domain.location.Distance;
import org.example.domain.location.Latitude;
import org.example.domain.location.Location;
import org.example.domain.location.Longitude;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

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
