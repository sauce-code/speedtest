package org.example.domain;

import java.net.URL;
import java.util.Objects;

public record Server(
        URL url,
        Location location,
        String city,
        String country,
        IsoAlpha2CountryCode countryCode,
        String sponsor,
        int id,
        String host
) {

    public Server {
        Objects.requireNonNull(url);
        Objects.requireNonNull(location);
        Objects.requireNonNull(city);
        Objects.requireNonNull(country);
        Objects.requireNonNull(countryCode);
        Objects.requireNonNull(sponsor);
        Objects.requireNonNull(host);
    }

}
