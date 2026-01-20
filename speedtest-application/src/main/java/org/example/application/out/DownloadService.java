package org.example.application.out;

import org.example.application.out.model.DownloadSetting;
import org.example.domain.TransferTestResult;

public interface DownloadService {

    TransferTestResult testDownload(String serverUrl, DownloadSetting settings);

}
