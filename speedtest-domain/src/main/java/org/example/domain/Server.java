package org.example.domain;

public record Server(
        String url,
        Location location,
        String city,
        String country,
        String isoAlpha2CountryCode,
        String sponsor,
        Integer id,
        String host
) {

}
