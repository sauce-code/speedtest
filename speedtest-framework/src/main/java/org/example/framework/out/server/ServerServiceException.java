package org.example.framework.out.server;

public class ServerServiceException extends RuntimeException {

    public ServerServiceException(Exception e) {
        super(e);
    }

}
