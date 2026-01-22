package org.example.domain;

import java.util.Arrays;

public enum DistanceUnit {
    MILE("mi"),
    KILOMETER("km"),
    NAUTICAL_MILE("nm");

    private final String abbreviation;

    public String getAbbreviation() {
        return abbreviation;
    }

    DistanceUnit(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public static DistanceUnit fromAbbreviation(String abbreviation) {
        return Arrays.stream(values())
                .filter(enumValue -> enumValue.getAbbreviation().equals(abbreviation))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
