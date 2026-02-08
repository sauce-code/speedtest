package org.example.application.out;

public interface LockService {

    boolean isBusy();

    boolean setBusy();

    void reset();

}
