package org.example.domain;

import org.example.util.Objectz;

public record Distance(
        double kilometers
) implements Comparable<Distance> {

    public Distance {
        Objectz.require(Double.isFinite(kilometers));
        Objectz.require(kilometers >= 0);
    }

    @Override
    public int compareTo(Distance o) {
        return Double.compare(kilometers, o.kilometers);
    }

}
