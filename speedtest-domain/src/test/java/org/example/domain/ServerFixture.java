package org.example.domain;

import org.example.domain.location.Latitude;
import org.example.domain.location.Location;
import org.example.domain.location.Longitude;

import java.net.URI;
import java.util.List;

public class ServerFixture {

    public static List<Server> some() {
        return List.of(
                new Server(
                        URI.create("http://ookla1.1und1-mobilfunk.de:8080/speedtest/upload.php"),
                        new Location(
                                Latitude.valueOf("49.9929"),
                                Longitude.valueOf("8.2473")),
                        "Mainz",
                        "Germany",
                        new IsoAlpha2CountryCode("DE"),
                        "1&1 Mobilfunk",
                        55133,
                        "ookla1.1und1-mobilfunk.de:8080"),
                new Server(
                        URI.create("http://de-ffm1-ookla-01.wemacom.net:8080/speedtest/upload.php"),
                        new Location(
                                Latitude.valueOf("50.1109"),
                                Longitude.valueOf("8.6821")),
                        "Frankfurt",
                        "Germany",
                        new IsoAlpha2CountryCode("DE"),
                        "WEMACOM Telekommunikation GmbH",
                        54577,
                        "de-ffm1-ookla-01.wemacom.net:8080"),
                new Server(
                        URI.create("http://speedtest.twerion.net:8080/speedtest/upload.php"),
                        new Location(
                                Latitude.valueOf("50.1109"),
                                Longitude.valueOf("8.6821")),
                        "Frankfurt",
                        "Germany",
                        new IsoAlpha2CountryCode("DE"),
                        "Twerion.net | Minecraft Server",
                        55462,
                        "speedtest.twerion.net:8080"));
    }

}
