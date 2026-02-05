package org.example.application.out;

import org.example.domain.ShareURL;

import java.net.URI;

public interface ImageStore {

    URI store(ShareURL shareURL);

}
