package org.example.framework.out.config;

import io.smallrye.config.ConfigMapping;

import java.net.URI;

@ConfigMapping(prefix = "speedtest.config")
public interface Properties {

    URI url();

}
