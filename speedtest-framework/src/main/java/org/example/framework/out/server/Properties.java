package org.example.framework.out.server;

import io.smallrye.config.ConfigMapping;
import jakarta.validation.constraints.NotEmpty;

import java.net.URI;
import java.util.List;

@ConfigMapping(prefix = "speedtest.server")
public interface Properties {

    @NotEmpty
    List<URI> baseUri();

}
