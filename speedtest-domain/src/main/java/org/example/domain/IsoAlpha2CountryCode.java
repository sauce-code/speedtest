package org.example.domain;

import org.example.util.Objectz;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public record IsoAlpha2CountryCode(
        String value
) {

    private static final List<String> codes = Arrays.asList(Locale.getISOCountries());

    public IsoAlpha2CountryCode {
        Objectz.require(codes.contains(value));
    }

}
