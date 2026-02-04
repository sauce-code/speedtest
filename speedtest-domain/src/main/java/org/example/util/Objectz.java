package org.example.util;

public class Objectz {

    private Objectz() {
        //
    }

    public static void require(boolean condition) {
        if (!condition) {
            throw new IllegalArgumentException();
        }
    }

}
