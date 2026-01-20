package org.example.application.out;

import org.example.domain.TransferTestResult;
import org.example.application.out.model.UploadSetting;

public interface UploadService {

    TransferTestResult testUpload(String serverUrl, UploadSetting settings, int threads) throws InterruptedException;

}
