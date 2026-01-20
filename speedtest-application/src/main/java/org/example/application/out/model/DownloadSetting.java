package org.example.application.out.model;

import org.example.domain.Client;

public record DownloadSetting(
        Integer testLength,
        Integer threadsPerUrl
) {

}
