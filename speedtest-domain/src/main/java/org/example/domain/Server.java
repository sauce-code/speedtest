package org.example.domain;

import java.net.URI;
import java.util.Objects;

public record Server(
        URI uri,
        Location location,
        String city,
        String country,
        IsoAlpha2CountryCode countryCode,
        String sponsor,
        int id,
        String host
) {

    public Server {
        Objects.requireNonNull(uri);
        Objects.requireNonNull(location);
        Objects.requireNonNull(city);
        Objects.requireNonNull(country);
        Objects.requireNonNull(countryCode);
        Objects.requireNonNull(sponsor);
        Objects.requireNonNull(host);
    }

}
