package org.example.domain;

public record Client(
        String ipAddress,
        Double lat,
        Double lon,
        String isp,
        Double ispRating,
        String isoAlpha2CountryCode
) {

}
