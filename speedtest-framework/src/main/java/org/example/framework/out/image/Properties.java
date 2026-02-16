package org.example.framework.out.image;

import io.smallrye.config.ConfigMapping;

import java.io.File;

@ConfigMapping(prefix = "speedtest.image")
public interface Properties {

    File path();

    int bufferSize();

}
