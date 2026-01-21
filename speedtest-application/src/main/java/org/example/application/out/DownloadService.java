package org.example.application.out;

import org.example.application.out.model.Download;
import org.example.domain.TransferTestResult;

public interface DownloadService {

    TransferTestResult testDownload(String serverUrl, Download settings) throws InterruptedException;

}
