package org.example.framework.out.transfer;

import org.example.application.out.DownloadService;
import org.example.domain.config.Download;
import org.example.domain.TransferTestResult;
import org.example.framework.out.http.HttpGetClient;
import org.example.util.Objectz;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        Objects.requireNonNull(serverUrl);
        Objects.requireNonNull(settings);
        List<String> urls = generateUrls(serverUrl, settings.threadsPerUrl());
        long timeoutTime = System.currentTimeMillis() + settings.testLength() * 1_000L;
        List<DownloadTask> callables = urls.stream()
                .map(s -> new DownloadTask(httpGetClient, s, timeoutTime))
                .toList();
        return transferService.testTransfer(callables, settings.threadsPerUrl() * 2);
    }

    private List<String> generateUrls(String serverUrl, int threadsPerUrl) {
        Objects.requireNonNull(serverUrl);
        Objectz.require(threadsPerUrl > 0);
        List<String> urls = new ArrayList<>();
        for (int size : SIZES) {
            for (int i = 0; i < threadsPerUrl; i++) {
                urls.add(String.format("%s/random%sx%s.jpg", serverUrl, size, size));
            }
        }
        return urls;
    }

}
