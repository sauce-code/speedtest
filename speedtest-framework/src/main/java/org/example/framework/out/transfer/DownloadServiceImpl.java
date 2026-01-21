package org.example.framework.out.transfer;

import org.example.application.out.DownloadService;
import org.example.application.out.model.Download;
import org.example.domain.TransferTestResult;
import org.example.framework.out.http.HttpGetClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class DownloadServiceImpl implements DownloadService {

    private static final int[] SIZES = new int[]{350, 500, 750, 1000, 1500, 2000, 2500, 3000, 3500, 4000};

    private final HttpGetClient httpGetClient;
    private final TransferService transferService;

    public DownloadServiceImpl(HttpGetClient httpGetClient, TransferService transferService) {
        this.httpGetClient = httpGetClient;
        this.transferService = transferService;
    }

    @Override
    public TransferTestResult testDownload(String serverUrl, Download settings) throws InterruptedException {
        if (serverUrl != null && settings != null) {
            List<String> urls = generateUrls(serverUrl, settings.threadsPerUrl());
            long timeoutTime = System.currentTimeMillis() + settings.testLength() * 1000L;
            List<Callable<TransferTestResult>> callables = new ArrayList<>();
            for (String url : urls) {
                callables.add(new DownloadTask(httpGetClient, url, timeoutTime));
            }
            return transferService.testTransfer(callables, settings.threadsPerUrl() * 2);
        } else {
            throw new IllegalArgumentException();
        }
    }

    private List<String> generateUrls(String serverUrl, int threadsPerUrl) {
        if (serverUrl != null && threadsPerUrl > 0) {
            List<String> urls = new ArrayList<>();
            for (int size : SIZES) {
                for (int iter = 0; iter < threadsPerUrl; iter++) {
                    urls.add(String.format("%s/random%sx%s.jpg", serverUrl, size, size));
                }
            }
            return urls;
        } else {
            throw new IllegalArgumentException();
        }
    }

}
