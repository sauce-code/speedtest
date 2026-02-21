package org.example.framework.out.transfer;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.application.out.DownloadService;
import org.example.domain.Server;
import org.example.domain.TransferTestResult;
import org.example.domain.config.DownloadSettings;
import org.example.framework.out.http.HttpGetClient;
import org.example.util.Objectz;

import java.net.URI;
import java.time.Clock;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class DownloadServiceImpl implements DownloadService {

    private final Properties properties;
    private final HttpGetClient httpGetClient;
    private final TransferService transferService;
    private final Clock clock;

    public DownloadServiceImpl(
            Properties properties,
            HttpGetClient httpGetClient,
            TransferService transferService,
            Clock clock) {
        this.properties = properties;
        this.httpGetClient = httpGetClient;
        this.transferService = transferService;
        this.clock = clock;
    }

    @Override
    public TransferTestResult testDownload(Server server, DownloadSettings settings) {
        Objects.requireNonNull(server);
        Objects.requireNonNull(settings);
        List<URI> uris = generateUrls(server.uri(), settings.threadsPerUrl());
        long timeoutTime = clock.millis() + settings.testLength() * 1_000L;
        List<DownloadTask> callables = uris.stream()
                .map(uri -> new DownloadTask(httpGetClient, uri, timeoutTime))
                .toList();
        return transferService.testTransfer(callables, settings.threadsPerUrl() * properties.download().threadFactor());
    }

    private List<URI> generateUrls(URI serverUri, int threadsPerUrl) {
        Objects.requireNonNull(serverUri);
        Objectz.require(threadsPerUrl > 0);
        List<URI> uris = new ArrayList<>();
        for (int size : properties.download().sizes()) {
            for (int i = 0; i < threadsPerUrl; i++) {
                String s = "%s/random%sx%s.jpg".formatted(serverUri, size, size);
                URI uri = URI.create(s);
                uris.add(uri);
            }
        }
        return uris;
    }

}
