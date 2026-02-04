package org.example.framework.out.transfer;

import org.example.domain.TransferTestResult;
import org.example.framework.out.http.HttpPostClient;

import java.net.URI;
import java.util.concurrent.Callable;

public final class UploadTask implements Callable<TransferTestResult> {

    private final HttpPostClient httpPostClient;
    private final URI uri;
    private final long timeoutTime;
    private final String dataString;

    public UploadTask(HttpPostClient httpPostClient, URI uri, long timeoutTime, String dataString) {
        this.httpPostClient = httpPostClient;
        this.uri = uri;
        this.timeoutTime = timeoutTime;
        this.dataString = dataString;
    }

    @Override
    public TransferTestResult call() {
        return httpPostClient.partialPostUploadData(uri, timeoutTime, dataString);
    }

}
