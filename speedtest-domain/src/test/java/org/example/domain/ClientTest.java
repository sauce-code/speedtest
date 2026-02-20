package org.example.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClientTest {

    @Test
    void givenServers_whenClosestServers_thenResult() {
        var client = ClientFixture.some();
        var servers = ServerFixture.some();
        var actual = client.closestServers(servers);
        var expected = ServerDistanceResultFixture.some();
        assertEquals(expected, actual);
    }

}
