package org.example.domain.location;

import org.example.util.Objectz;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public record Latitude(
        BigDecimal value
) {

    static final int SCALE = 4;
    static final BigDecimal MAX = new BigDecimal("90");
    static final BigDecimal MIN = new BigDecimal("-90");

    public Latitude {
        Objects.requireNonNull(value);
        Objectz.require(value.scale() == SCALE);
        Objectz.require(value.compareTo(MAX) <= 0);
        Objectz.require(value.compareTo(MIN) >= 0);
    }

    public static Latitude valueOf(String s) {
        var value = new BigDecimal(s).setScale(SCALE, RoundingMode.HALF_UP);
        return new Latitude(value);
    }

}
