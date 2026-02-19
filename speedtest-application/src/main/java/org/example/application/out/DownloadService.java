package org.example.application.out;

import org.example.domain.Server;
import org.example.domain.TransferTestResult;
import org.example.domain.config.DownloadSettings;

public interface DownloadService {

    TransferTestResult testDownload(Server server, DownloadSettings settings);

}
