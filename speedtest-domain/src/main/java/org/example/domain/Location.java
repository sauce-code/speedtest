package org.example.domain;

import java.util.Objects;

public record Location(
        double latitude,
        double longitude
) {

    public Distance distance(Location other) {
        Objects.requireNonNull(other);
        double theta = longitude - other.longitude;
        double dist = Math.sin(Math.toRadians(latitude)) * Math.sin(Math.toRadians(other.latitude))
                + Math.cos(Math.toRadians(latitude)) * Math.cos(Math.toRadians(other.latitude)) * Math.cos(Math.toRadians(theta));
        dist = Math.acos(dist);
        dist = Math.toDegrees(dist);
        dist = dist * 60 * 1.1515; // miles
        dist = dist * 1.609344; // km
        return new Distance(dist);
    }

}
