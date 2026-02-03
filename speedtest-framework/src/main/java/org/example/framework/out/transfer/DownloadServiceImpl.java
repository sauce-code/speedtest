package org.example.framework.out.transfer;

import org.example.application.out.DownloadService;
import org.example.application.out.TimeService;
import org.example.domain.Server;
import org.example.domain.config.DownloadSettings;
import org.example.domain.TransferTestResult;
import org.example.framework.out.http.HttpGetClient;
import org.example.util.Objectz;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DownloadServiceImpl implements DownloadService {

    private static final int[] SIZES = new int[]{350, 500, 750, 1000, 1500, 2000, 2500, 3000, 3500, 4000};

    private final HttpGetClient httpGetClient;
    private final TransferService transferService;
    private final TimeService timeService;

    public DownloadServiceImpl(HttpGetClient httpGetClient, TransferService transferService, TimeService timeService) {
        this.httpGetClient = httpGetClient;
        this.transferService = transferService;
        this.timeService = timeService;
    }

    @Override
    public TransferTestResult testDownload(Server server, DownloadSettings settings) throws InterruptedException {
        Objects.requireNonNull(server);
        Objects.requireNonNull(settings);
        List<URL> urls = generateUrls(server.url(), settings.threadsPerUrl());
        long timeoutTime = timeService.currentTimeMillis() + settings.testLength() * 1_000L;
        List<DownloadTask> callables = urls.stream()
                .map(url -> new DownloadTask(httpGetClient, url, timeoutTime))
                .toList();
        return transferService.testTransfer(callables, settings.threadsPerUrl() * 2);
    }

    private List<URL> generateUrls(URL serverUrl, int threadsPerUrl) {
        Objects.requireNonNull(serverUrl);
        Objectz.require(threadsPerUrl > 0);
        List<URL> urls = new ArrayList<>();
        for (int size : SIZES) {
            for (int i = 0; i < threadsPerUrl; i++) {
                String s = "%s/random%sx%s.jpg".formatted(serverUrl, size, size);
                URI uri = URI.create(s);
                URL url = Objectz.notThrows(uri::toURL);
                urls.add(url);
            }
        }
        return urls;
    }

}
