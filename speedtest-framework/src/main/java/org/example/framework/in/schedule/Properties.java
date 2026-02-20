package org.example.framework.in.schedule;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "speedtest.schedule")
public interface Properties {

    boolean enabled();

    String cron();

}
