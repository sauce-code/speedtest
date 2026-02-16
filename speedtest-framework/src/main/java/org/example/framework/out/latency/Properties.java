package org.example.framework.out.latency;

import io.smallrye.config.ConfigMapping;
import jakarta.validation.constraints.Positive;

@ConfigMapping(prefix = "speedtest.latency")
public interface Properties {

    @Positive
    int testsPerServer();

}
