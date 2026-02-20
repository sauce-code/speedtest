package org.example.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ISPRatingTest {

    @ParameterizedTest
    @ValueSource(strings = {"-1.23", "-12.344", "-.2"})
    void givenInvalidValue_whenConstructor_thenThrows(String value) {
        var bigDecimal = new BigDecimal(value);
        assertThrows(IllegalArgumentException.class, () -> new ISPRating(bigDecimal));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1.2", "2.3", "0", ".0", "-0", "-.0"})
    void givenValidValue_whenConstructor_thenNotThrows(String value) {
        var bigDecimal = new BigDecimal(value);
        assertDoesNotThrow(() -> new ISPRating(bigDecimal));
    }

}
