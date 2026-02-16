package org.example.framework.out.transfer;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.application.out.TimeService;
import org.example.application.out.UploadService;
import org.example.domain.Server;
import org.example.domain.TransferTestResult;
import org.example.domain.config.UploadSettings;
import org.example.framework.out.http.HttpPostClient;
import org.example.util.Objectz;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class UploadServiceImpl implements UploadService {

    private static final String CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String CONTENT = "content1=";

    private final Properties properties;
    private final HttpPostClient httpPostClient;
    private final TransferService transferService;
    private final TimeService timeService;

    public UploadServiceImpl(
            Properties properties,
            HttpPostClient httpPostClient,
            TransferService transferService,
            TimeService timeService) {
        this.properties = properties;
        this.httpPostClient = httpPostClient;
        this.transferService = transferService;
        this.timeService = timeService;
    }

    @Override
    public TransferTestResult testUpload(Server server, UploadSettings settings) throws InterruptedException {
        Objects.requireNonNull(server);
        Objects.requireNonNull(settings);
        int length = properties.upload().sizes().size();
        List<Integer> uploadSizes = properties.upload().sizes().subList(settings.ratio() - 1, length);
        int uploadCount = (int) Math.ceil((double) settings.maxChunkCount() / (double) uploadSizes.size());
        List<Integer> sizeList = new ArrayList<>();
        for (int size : uploadSizes) {
            for (int iter = 0; iter < uploadCount; iter++) {
                sizeList.add(size);
            }
        }
        long timeoutTime = timeService.currentTimeMillis() + settings.testLength() * 1_000L;
        List<UploadTask> callables = sizeList.stream()
                .map(s -> new UploadTask(httpPostClient, server.uri(), timeoutTime, generateDataString(s)))
                .toList();
        return transferService.testTransfer(callables, settings.threads() * properties.upload().threadFactor());
    }

    private String generateDataString(int size) {
        Objectz.require(size > 0);
        int multiplier = (int) Math.ceil(size / (float) CHARS.length());
        StringBuilder dataString = new StringBuilder(CONTENT);
        dataString.append(CHARS.repeat(Math.max(0, multiplier)));
        return dataString.substring(0, size);
    }

}
