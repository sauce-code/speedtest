package org.example.domain.config;

public record DownloadSettings(
        int testLength,
        int threadsPerUrl
) {
}
