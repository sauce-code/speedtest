package org.example.domain;

import org.example.util.Objectz;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public record Longitude(
        BigDecimal value
) {

    static final int SCALE = 4;
    static final BigDecimal MAX = new BigDecimal("180");
    static final BigDecimal MIN = new BigDecimal("-180");

    public Longitude {
        Objects.requireNonNull(value);
        Objectz.require(value.scale() == SCALE);
        Objectz.require(value.compareTo(MAX) <= 0);
        Objectz.require(value.compareTo(MIN) >= 0);
    }

    public static Longitude valueOf(String s) {
        var value = new BigDecimal(s).setScale(SCALE, RoundingMode.HALF_UP);
        return new Longitude(value);
    }

}
