package org.example.framework.out.time;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.application.out.TimeService;

import java.time.LocalDateTime;

@ApplicationScoped
public class TimeServiceImpl implements TimeService {

    @Override
    public LocalDateTime localDateTime() {
        return LocalDateTime.now();
    }

    @Override
    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }

}
