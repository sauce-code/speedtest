package org.example.framework.out.lock;

import jakarta.inject.Singleton;
import org.example.application.out.LockService;

import java.util.concurrent.atomic.AtomicBoolean;

@Singleton
public class LockServiceImpl implements LockService {

    private final AtomicBoolean locked = new AtomicBoolean(false);

    @Override
    public boolean isBusy() {
        return locked.get();
    }

    @Override
    public boolean setBusy() {
        return locked.compareAndSet(false, true);
    }

    @Override
    public void reset() {
        locked.set(false);
    }

}
