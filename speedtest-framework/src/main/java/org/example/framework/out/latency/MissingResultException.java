package org.example.framework.out.latency;

public final class MissingResultException extends RuntimeException {

    public MissingResultException(final String message) {
        super(message);
    }

    public MissingResultException() {
    }

}
