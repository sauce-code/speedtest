package org.example.framework.out.http;

import io.smallrye.config.ConfigMapping;
import jakarta.validation.constraints.Positive;

@ConfigMapping(prefix = "speedtest.http")
public interface Properties {

    boolean useCaches();

    String userAgent();

    String connection();

    String cacheControl();

    Download download();

    Upload upload();

    interface Download {

        @Positive
        int maxBufferSize();

    }

    interface Upload {

        @Positive
        int maxBufferSize();

        String referer();

        String contentType();

    }

}
