package org.example.application.in;

public class ApplicationRunException extends RuntimeException {

    public ApplicationRunException(String message) {
        super(message);
    }

    public ApplicationRunException(Exception e) {
        super(e);
    }

}
