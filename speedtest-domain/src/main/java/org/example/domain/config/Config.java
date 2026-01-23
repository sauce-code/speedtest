package org.example.domain.config;

import org.example.domain.Client;

public record Config(
    Client client,
    DownloadSettings downloadSettings,
    UploadSettings uploadSettings
) {

}
