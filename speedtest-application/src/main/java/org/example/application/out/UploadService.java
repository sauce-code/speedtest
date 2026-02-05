package org.example.application.out;

import org.example.domain.Server;
import org.example.domain.TransferTestResult;
import org.example.domain.config.UploadSettings;

public interface UploadService {

    TransferTestResult testUpload(Server server, UploadSettings settings) throws InterruptedException;

}
