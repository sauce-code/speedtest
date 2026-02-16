package org.example.framework.out.transfer;

import io.smallrye.config.ConfigMapping;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

import java.util.List;

@ConfigMapping(prefix = "speedtest.transfer")
public interface Properties {

    Download download();

    Upload upload();

    interface Download {

        @NotEmpty
        List<Integer> sizes();

        @Positive
        int threadFactor();

    }

    interface Upload {

        @NotEmpty
        List<Integer> sizes();

        @Positive
        int threadFactor();

    }

}
