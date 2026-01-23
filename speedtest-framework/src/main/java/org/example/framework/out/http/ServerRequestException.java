package org.example.framework.out.http;

public final class ServerRequestException extends RuntimeException {
    public ServerRequestException(final String message) {
        super(message);
    }

    public ServerRequestException(final Exception exception) {
        super(exception);
    }
}
