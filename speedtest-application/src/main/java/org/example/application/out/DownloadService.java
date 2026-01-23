package org.example.application.out;

import org.example.domain.Server;
import org.example.domain.config.Download;
import org.example.domain.TransferTestResult;

public interface DownloadService {

    TransferTestResult testDownload(Server server, Download settings) throws InterruptedException;

}
