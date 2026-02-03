package org.example.framework.out.transfer;

import org.example.domain.TransferTestResult;
import org.example.framework.out.http.HttpGetClient;

import java.net.URL;
import java.util.concurrent.Callable;

public class DownloadTask implements Callable<TransferTestResult> {

    private final HttpGetClient httpGetClient;
    private final URL url;
    private final long timeoutTime;

    public DownloadTask(HttpGetClient httpGetClient, URL url, long timeoutTime) {
        this.httpGetClient = httpGetClient;
        this.url = url;
        this.timeoutTime = timeoutTime;
    }

    @Override
    public TransferTestResult call() {
        return httpGetClient.partialGetDownloadData(url, timeoutTime);
    }

}
