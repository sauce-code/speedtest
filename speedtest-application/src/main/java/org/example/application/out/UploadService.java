package org.example.application.out;

import org.example.domain.config.Upload;
import org.example.domain.TransferTestResult;

public interface UploadService {

    TransferTestResult testUpload(String serverUrl, Upload settings, int threads) throws InterruptedException;

}
