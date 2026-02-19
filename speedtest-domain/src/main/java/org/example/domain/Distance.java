package org.example.domain;

import org.example.util.Objectz;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public record Distance(
        BigDecimal kilometers
) implements Comparable<Distance> {

    static final BigDecimal KM_TO_MILES = new BigDecimal("0.6213711922");
    static final int SCALE = 6;

    public Distance {
        Objects.requireNonNull(kilometers);
        Objectz.require(kilometers.scale() == SCALE);
        Objectz.require(kilometers.signum() >= 0);
    }

    @Override
    public int compareTo(Distance o) {
        return kilometers.compareTo(o.kilometers);
    }

    public BigDecimal miles() {
        return kilometers.multiply(KM_TO_MILES).setScale(SCALE, RoundingMode.HALF_UP);
    }

    public static Distance valueOf(String s) {
        var value = new BigDecimal(s).setScale(SCALE, RoundingMode.HALF_UP);
        return new Distance(value);
    }

    public static Distance valueOf(double d) {
        var value = BigDecimal.valueOf(d).setScale(SCALE, RoundingMode.HALF_UP);
        return new Distance(value);
    }

}
