package org.example.application.out;

import org.example.domain.ShareURL;

import java.io.File;
import java.net.URI;

public interface ImageStore {

    File store(ShareURL shareURL);

}
