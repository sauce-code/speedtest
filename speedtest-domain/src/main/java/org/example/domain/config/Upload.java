package org.example.domain.config;

public record Upload(
        int ratio,
        int maxChunkCount,
        int threads,
        int testLength
) {

}
