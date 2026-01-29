package org.example.domain;

import java.net.URL;

public record Server(
        URL url,
        Location location,
        String city,
        String country,
        IsoAlpha2CountryCode isoAlpha2CountryCode,
        String sponsor,
        Integer id,
        String host
) {

}
