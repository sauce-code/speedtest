package org.example.framework.out.clock;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

import java.time.Clock;

@ApplicationScoped
public class ClockProvider {

    private final Properties properties;

    public ClockProvider(Properties properties) {
        this.properties = properties;
    }

    @Produces
    public Clock clock() {
        return Clock.system(properties.zoneId());
    }

}
