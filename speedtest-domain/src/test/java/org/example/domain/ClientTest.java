package org.example.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClientTest {

    @Test
    void givenServers_whenServerDistances_thenResult() {
        var client = ClientFixture.some();
        var servers = ServerFixture.some();
        var actual = client.serverDistances(servers);
        var expected = ServerDistanceFixture.some();
        assertEquals(expected, actual);
    }

}
