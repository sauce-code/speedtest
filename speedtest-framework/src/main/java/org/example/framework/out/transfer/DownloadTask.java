package org.example.framework.out.transfer;

import org.example.domain.TransferTestResult;
import org.example.framework.out.http.HttpGetClient;

import java.net.URI;
import java.util.concurrent.Callable;

public class DownloadTask implements Callable<TransferTestResult> {

    private final HttpGetClient httpGetClient;
    private final URI uri;
    private final long timeoutTime;

    public DownloadTask(
            HttpGetClient httpGetClient,
            URI uri,
            long timeoutTime) {
        this.httpGetClient = httpGetClient;
        this.uri = uri;
        this.timeoutTime = timeoutTime;
    }

    @Override
    public TransferTestResult call() {
        return httpGetClient.partialGetDownloadData(uri, timeoutTime);
    }

}
