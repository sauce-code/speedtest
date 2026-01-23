package org.example.domain;

public record Client(
        String ipAddress,
        Location location,
        String isp,
        Double ispRating,
        String isoAlpha2CountryCode
) {

}
