package org.example.domain;

public record Server(
        String url,
        Double lat,
        Double lon,
        String city,
        String country,
        String isoAlpha2CountryCode,
        String sponsor,
        Integer id,
        String host
) {

}
