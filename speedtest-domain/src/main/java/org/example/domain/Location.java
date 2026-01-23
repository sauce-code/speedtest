package org.example.domain;

import java.util.Objects;

public record Location(
        double latitude,
        double longitude
) {

    public double distance(Location other, DistanceUnit distanceUnit) {
        Objects.requireNonNull(distanceUnit);
        double theta = longitude - other.longitude;
        double dist = Math.sin(Math.toRadians(latitude)) * Math.sin(Math.toRadians(other.latitude))
                + Math.cos(Math.toRadians(latitude)) * Math.cos(Math.toRadians(other.latitude)) * Math.cos(Math.toRadians(theta));
        dist = Math.acos(dist);
        dist = Math.toDegrees(dist);
        dist = dist * 60 * 1.1515; // miles
        return switch (distanceUnit) {
            case MILE -> dist;
            case KILOMETER -> dist * 1.609344;
            case NAUTICAL_MILE -> dist * 0.8684;
        };
    }

}
