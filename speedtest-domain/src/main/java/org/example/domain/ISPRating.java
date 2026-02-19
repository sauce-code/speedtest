package org.example.domain;

import org.example.util.Objectz;

import java.math.BigDecimal;
import java.util.Objects;

public record ISPRating(
        BigDecimal value
) {

    public ISPRating {
        Objects.requireNonNull(value);
        Objectz.require(value.signum() >= 0);
    }

}
