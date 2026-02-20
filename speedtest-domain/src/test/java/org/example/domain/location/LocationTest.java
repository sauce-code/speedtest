package org.example.domain.location;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LocationTest {

    @Test
    void givenPositives_whenDistance_thenDistance() {
        var client = new Location(Latitude.valueOf("51.0880"), Longitude.valueOf("6.8845"));
        var server = new Location(Latitude.valueOf("51.2256"), Longitude.valueOf("6.7828"));
        var actual = client.distance(server);
        var expected = Distance.valueOf("16.864419");
        assertEquals(expected, actual);
    }

    @Test
    void givenNegatives_whenDistance_thenDistance() {
        var client = new Location(Latitude.valueOf("-51.0880"), Longitude.valueOf("-6.8845"));
        var server = new Location(Latitude.valueOf("-51.2256"), Longitude.valueOf("-6.7828"));
        var actual = client.distance(server);
        var expected = Distance.valueOf("16.864419");
        assertEquals(expected, actual);
    }

}
