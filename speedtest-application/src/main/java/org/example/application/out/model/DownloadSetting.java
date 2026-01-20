package org.example.application.out.model;

import org.example.domain.Client;

public record DownloadSetting(
        Client client,
        DownloadSetting download,
        UploadSetting upload
) {

}
