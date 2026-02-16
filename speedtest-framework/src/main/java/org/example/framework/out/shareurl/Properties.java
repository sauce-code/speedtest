package org.example.framework.out.shareurl;

import io.smallrye.config.ConfigMapping;

import java.net.URI;

@ConfigMapping(prefix = "speedtest.share")
public interface Properties {

    URI url();

}
