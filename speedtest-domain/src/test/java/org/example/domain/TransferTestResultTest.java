package org.example.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransferTestResultTest {

    @Test
    void givenResult_whenMbps_thenResult() {
        var given = new TransferTestResult(2048, 1023L);
        var actual = given.rateInMbps();
        var expected = 0.01601564027370479;
        assertEquals(expected, actual);
    }

}
