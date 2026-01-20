package org.example.application.out.model;

import org.example.domain.Client;

public record Config(
        Client client,
        DownloadSetting download,
        UploadSetting upload
) {

}
