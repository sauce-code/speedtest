package org.example.application.out;

import org.example.domain.config.Download;
import org.example.domain.TransferTestResult;

public interface DownloadService {

    TransferTestResult testDownload(String serverUrl, Download settings) throws InterruptedException;

}
