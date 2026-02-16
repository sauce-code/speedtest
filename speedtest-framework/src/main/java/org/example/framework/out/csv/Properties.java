package org.example.framework.out.csv;

import io.smallrye.config.ConfigMapping;

import java.io.File;
import java.nio.charset.Charset;

@ConfigMapping(prefix = "speedtest.csv")
public interface Properties {

    File file();

    Charset charset();

    String delimiter();

    String rowSeparator();

}
