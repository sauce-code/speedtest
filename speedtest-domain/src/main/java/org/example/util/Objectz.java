package org.example.util;

import java.util.concurrent.Callable;

public class Objectz {

    private Objectz() {
        //
    }

    public static void require(boolean condition) {
        if (!condition) {
            throw new IllegalArgumentException();
        }
    }

    public static <T> T notThrows(Callable<T> callable) {
        try {
            return callable.call();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

}
