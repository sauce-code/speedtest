package org.example.domain.config;

import org.example.domain.ClientFixture;

public class ConfigFixture {

    public static Config some() {
        return new Config(
                ClientFixture.some(),
                new DownloadSettings(10, 4),
                new UploadSettings(5, 50, 2, 10));
    }

}
