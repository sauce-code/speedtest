package org.example.domain;

import org.example.util.Objectz;

import java.util.Objects;

public record Location(
        double latitude,
        double longitude
) {

    public Location {
        Objectz.require(Double.isFinite(latitude));
        Objectz.require(latitude >= -90d);
        Objectz.require(latitude <= 90d);
        Objectz.require(Double.isFinite(longitude));
        Objectz.require(longitude >= -180d);
        Objectz.require(longitude <= 180d);
    }

    public Distance distance(Location other) {
        Objects.requireNonNull(other);
        if (equals(other)) {
            return new Distance(0d);
        }
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
