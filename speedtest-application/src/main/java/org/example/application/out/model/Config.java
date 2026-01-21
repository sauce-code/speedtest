package org.example.application.out.model;

import org.example.domain.Client;

public record Config(
    Client client,
    Download download,
    Upload upload
) {

}
