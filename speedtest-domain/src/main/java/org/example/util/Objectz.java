package org.example.util;

import java.util.concurrent.Callable;
import java.util.function.Function;

public class Objectz {

    private Objectz() {
        //
    }

    public static void require(boolean condition) {
        if (!condition) {
            throw new IllegalArgumentException();
        }
    }

    public static <T> T notThrows(Callable<T> function) {
        try {
            return function.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T, R> R notThrows(Function<T, R> function, T t) {
        return function.apply(t);
    }

}
