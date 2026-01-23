package org.example.application.out;

import org.example.domain.Server;
import org.example.domain.config.DownloadSettings;
import org.example.domain.TransferTestResult;

public interface DownloadService {

    TransferTestResult testDownload(Server server, DownloadSettings settings) throws InterruptedException;

}
