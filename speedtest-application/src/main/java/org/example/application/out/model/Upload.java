package org.example.application.out.model;

public record Upload(
        int ratio,
        int maxChunkCount,
        int threads,
        int testLength
) {

}
