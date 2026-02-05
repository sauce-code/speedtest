package org.example.framework.out.transfer;

import org.example.application.out.TimeService;
import org.example.application.out.UploadService;
import org.example.domain.Server;
import org.example.domain.TransferTestResult;
import org.example.domain.config.UploadSettings;
import org.example.framework.out.http.HttpPostClient;
import org.example.util.Objectz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class UploadServiceImpl implements UploadService {

    private static final int[] SIZES = new int[]{32768, 65536, 131072, 262144, 524288, 1048576, 7340032};
    private static final String CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String CONTENT = "content1=";

    private final HttpPostClient httpPostClient;
    private final TransferService transferService;
    private final TimeService timeService;

    public UploadServiceImpl(HttpPostClient httpPostClient, TransferService transferService, TimeService timeService) {
        this.httpPostClient = httpPostClient;
        this.transferService = transferService;
        this.timeService = timeService;
    }

    @Override
    public TransferTestResult testUpload(Server server, UploadSettings settings) throws InterruptedException {
        Objects.requireNonNull(server);
        Objects.requireNonNull(settings);
        int[] uploadSizes = Arrays.copyOfRange(SIZES, settings.ratio() - 1, SIZES.length);
        int uploadCount = (int) Math.ceil((double) settings.maxChunkCount() / (double) uploadSizes.length);
        List<Integer> sizeList = new ArrayList<>();
        for (int size : uploadSizes) {
            for (int iter = 0; iter < uploadCount; iter++) {
                sizeList.add(size);
            }
        }
        long timeoutTime = timeService.currentTimeMillis() + settings.testLength() * 1000L;
        List<UploadTask> callables = sizeList.stream()
                .map(s -> new UploadTask(httpPostClient, server.uri(), timeoutTime, generateDataString(s)))
                .toList();
        return transferService.testTransfer(callables, 8); // TODO warum 8 Threads?
    }

    private String generateDataString(int size) {
        Objectz.require(size > 0);
        int multiplier = (int) Math.ceil(size / (float) CHARS.length());
        StringBuilder dataString = new StringBuilder(CONTENT);
        dataString.append(CHARS.repeat(Math.max(0, multiplier)));
        return dataString.substring(0, size);
    }

}
