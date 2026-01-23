package org.example.framework.out.transfer;

public final class ServerRequestException extends Exception {
    public ServerRequestException(final String message) {
        super(message);
    }

    public ServerRequestException(final Exception exception) {
        super(exception);
    }
}
