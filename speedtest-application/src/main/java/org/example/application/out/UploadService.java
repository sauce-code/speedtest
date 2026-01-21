package org.example.application.out;

import org.example.application.out.model.Upload;
import org.example.domain.TransferTestResult;

public interface UploadService {

    TransferTestResult testUpload(String serverUrl, Upload settings, int threads) throws InterruptedException;

}
