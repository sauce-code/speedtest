package org.example.domain;

import org.example.util.Objectz;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public record Latency(
        BigDecimal ms
) implements Comparable<Latency> {

    static final int SCALE = 2;

    public Latency {
        Objects.requireNonNull(ms);
        Objectz.require(ms.signum() >= 0);
        Objectz.require(ms.scale() == SCALE);
    }

    public static Latency valueOf(String s) {
        var value = new BigDecimal(s).setScale(SCALE, RoundingMode.HALF_UP);
        return new Latency(value);
    }

    public static Latency valueOf(double d) {
        var value = BigDecimal.valueOf(d).setScale(SCALE, RoundingMode.HALF_UP);
        return new Latency(value);
    }

    @Override
    public int compareTo(Latency o) {
        return ms.compareTo(o.ms);
    }

}
