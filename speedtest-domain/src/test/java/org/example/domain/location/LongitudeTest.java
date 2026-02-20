package org.example.domain.location;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class LongitudeTest {

    @ParameterizedTest
    @ValueSource(strings = {"-180.0001", "180.0001", "12", "12.345", "12.34567"})
    void givenInvalidValue_whenConstructor_thenThrows(String value) {
        var bigDecimal = new BigDecimal(value);
        assertThrows(IllegalArgumentException.class, () -> new Longitude(bigDecimal));
    }

    @ParameterizedTest
    @ValueSource(strings = {"-180.0000", "180.0000", "0.0000", "1.1234", "-12.1234"})
    void givenValidValue_whenConstructor_thenNotThrows(String value) {
        var bigDecimal = new BigDecimal(value);
        assertDoesNotThrow(() -> new Longitude(bigDecimal));
    }

    @Test
    void givenValue_whenValueOf_thenRecord() {
        var given = "123.12345";
        var actual = Longitude.valueOf(given);
        var expected = new Longitude(new BigDecimal("123.1235"));
        assertEquals(expected, actual);
    }

}
