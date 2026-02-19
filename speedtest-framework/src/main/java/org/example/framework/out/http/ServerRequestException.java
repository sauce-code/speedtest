package org.example.framework.out.http;

public class ServerRequestException extends Exception {

    public ServerRequestException(Exception exception) {
        super(exception);
    }

}
