package org.example.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class DistanceTest {

    @ParameterizedTest
    @ValueSource(strings = {"-1.000000", "-0.000001"})
    void givenInvalidValue_whenConstructor_thenThrows(String value) {
        var bigDecimal = new BigDecimal(value);
        assertThrows(IllegalArgumentException.class, () -> new Distance(bigDecimal));
    }

    @ParameterizedTest
    @ValueSource(strings = {"-0.000000", "0.000000", "0.000001", "1.000000"})
    void givenValidValue_whenConstructor_thenNotThrows(String value) {
        var bigDecimal = new BigDecimal(value);
        assertDoesNotThrow(() -> new Distance(bigDecimal));
    }

    @Test
    void givenValueString_whenValueOf_thenRecord() {
        var given = "12.1234567";
        var actual = Distance.valueOf(given);
        var expected = new Distance(new BigDecimal("12.123457"));
        assertEquals(expected, actual);
    }

    @Test
    void givenValueDouble_whenValueOf_thenRecord() {
        var d = 12.1234567;
        var actual = Distance.valueOf(d);
        var expected = new Distance(new BigDecimal("12.123457"));
        assertEquals(expected, actual);
    }

}
