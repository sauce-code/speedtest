package org.example.domain.config;

public record UploadSettings(
        int ratio,
        int maxChunkCount,
        int threads,
        int testLength
) {

}
