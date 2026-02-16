package org.example.framework.out.csv;

import io.smallrye.config.ConfigMapping;

import java.io.File;

@ConfigMapping(prefix = "speedtest.csv")
public interface Properties {

    File file();

}
