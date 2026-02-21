package org.example.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class LatencyTest {

    @ParameterizedTest
    @ValueSource(strings = {"-1.00", "-0.01"})
    void givenInvalidValue_whenConstructor_thenThrows(String value) {
        var bigDecimal = new BigDecimal(value);
        assertThrows(IllegalArgumentException.class, () -> new Latency(bigDecimal));
    }

    @ParameterizedTest
    @ValueSource(strings = {"-0.00", "0.00", "0.01", "1.00"})
    void givenValidValue_whenConstructor_thenNotThrows(String value) {
        var bigDecimal = new BigDecimal(value);
        assertDoesNotThrow(() -> new Latency(bigDecimal));
    }

    @Test
    void givenValueString_whenValueOf_thenRecord() {
        var given = "12.12";
        var actual = Latency.valueOf(given);
        var expected = new Latency(new BigDecimal("12.12"));
        assertEquals(expected, actual);
    }

    @Test
    void givenValueDouble_whenValueOf_thenRecord() {
        var d = 12.12;
        var actual = Latency.valueOf(d);
        var expected = new Latency(new BigDecimal("12.12"));
        assertEquals(expected, actual);
    }

}
