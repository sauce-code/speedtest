package org.example.application.out.model;

import org.example.domain.Client;

public record UploadSetting(
        Integer ratio,
        Integer maxChunkCount,
        Integer threads,
        Integer testLength
) {

}
