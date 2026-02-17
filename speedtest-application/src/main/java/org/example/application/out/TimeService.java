package org.example.application.out;

import java.time.LocalDateTime;

public interface TimeService { // TODO replace with clock

    LocalDateTime localDateTime();

    long currentTimeMillis();

}
