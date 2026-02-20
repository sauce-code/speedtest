package org.example.domain.location;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class LatitudeTest {

    @ParameterizedTest
    @ValueSource(strings = {"-90.0001", "90.0001", "12", "12.345", "12.34567"})
    void givenInvalidValue_whenConstructor_thenThrows(String value) {
        var bigDecimal = new BigDecimal(value);
        assertThrows(IllegalArgumentException.class, () -> new Latitude(bigDecimal));
    }

    @ParameterizedTest
    @ValueSource(strings = {"-90.0000", "90.0000", "0.0000", "1.2345", "-12.3456"})
    void givenValidValue_whenConstructor_thenNotThrows(String value) {
        var bigDecimal = new BigDecimal(value);
        assertDoesNotThrow(() -> new Latitude(bigDecimal));
    }

    @Test
    void givenValue_whenValueOf_thenRecord() {
        var given = "12.12345";
        var actual = Latitude.valueOf(given);
        var expected = new Latitude(new BigDecimal("12.1235"));
        assertEquals(expected, actual);
    }

}
