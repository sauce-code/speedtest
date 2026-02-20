package org.example.framework.out.clock;

import io.smallrye.config.ConfigMapping;

import java.time.ZoneId;

@ConfigMapping(prefix = "speedtest.clock")
public interface Properties {

    ZoneId zoneId();

}
