package org.example.application.out;

import org.example.domain.Server;
import org.example.domain.config.Upload;
import org.example.domain.TransferTestResult;

public interface UploadService {

    TransferTestResult testUpload(Server server, Upload settings) throws InterruptedException;

}
