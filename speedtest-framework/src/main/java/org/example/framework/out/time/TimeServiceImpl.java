package org.example.framework.out.time;

import org.example.application.out.TimeService;

import java.time.LocalDateTime;

public class TimeServiceImpl implements TimeService {

    @Override
    public LocalDateTime localDateTime() {
        return LocalDateTime.now();
    }

}
