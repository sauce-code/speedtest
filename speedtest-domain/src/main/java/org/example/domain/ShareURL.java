package org.example.domain;

import java.net.URI;
import java.util.Objects;

public record ShareURL(
        URI uri
) {

    public ShareURL {
        Objects.requireNonNull(uri);
    }

}
