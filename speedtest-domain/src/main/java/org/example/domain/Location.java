package org.example.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public record Location(
        Latitude latitude,
        Longitude longitude
) {

    static final double RADIUS_EARTH_KM = 6_371.0087714;

    public Location {
        Objects.requireNonNull(latitude);
        Objects.requireNonNull(longitude);
    }

    public Distance distance(Location other) {
        Objects.requireNonNull(other);
        if (equals(other)) {
            return new Distance(new BigDecimal("0.00"));
        }
        var lat1 = latitude.value().doubleValue();
        var lat2 = other.latitude.value().doubleValue();
        var lon1 = longitude.value().doubleValue();
        var lon2 = other.longitude.value().doubleValue();

        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distance = RADIUS_EARTH_KM * c; // Distance in kilometers
        return Distance.valueOf(distance);
    }


}
