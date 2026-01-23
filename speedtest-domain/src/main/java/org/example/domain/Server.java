package org.example.domain;

import java.net.URL;

public record Server(
        URL url,
        Location location,
        String city,
        String country,
        String isoAlpha2CountryCode,
        String sponsor,
        Integer id,
        String host
) {

}
