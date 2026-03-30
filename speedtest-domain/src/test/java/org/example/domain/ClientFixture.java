package org.example.domain;

import org.example.domain.location.Latitude;
import org.example.domain.location.Location;
import org.example.domain.location.Longitude;

import java.math.BigDecimal;

public class ClientFixture {

    public static Client some() {
        return new Client(
                "134.19.32.81",
                new Location(
                        Latitude.valueOf("49.0401"),
                        Longitude.valueOf("8.7109")),
                "vitroconnect",
                new ISPRating(new BigDecimal("3.7")),
                new IsoAlpha2CountryCode("DE"));
    }

}
