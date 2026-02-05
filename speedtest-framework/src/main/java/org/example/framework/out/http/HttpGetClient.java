package org.example.framework.out.http;

import org.apache.commons.io.IOUtils;
import org.example.application.out.TimeService;
import org.example.domain.TransferTestResult;
import org.example.framework.out.Util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Objects;

public class HttpGetClient {

    private static final String GET = "GET";

    private final HttpClient httpClient;
    private final TimeService timeService;

    public HttpGetClient(HttpClient httpClient, TimeService timeService) {
        this.httpClient = httpClient;
        this.timeService = timeService;
    }

    public TransferTestResult partialGetDownloadData(URI uri, long timeoutTime) {
        Objects.requireNonNull(uri);
        int bytesReceived = 0;
        try {
            HttpURLConnection conn = httpClient.createConnection(uri, GET);
            long startTime = timeService.currentTimeMillis();
            try (InputStream is = conn.getInputStream()) {
                byte[] buffer =
                        new byte[Integer.parseInt(Objects.requireNonNull(Util.getConfigProperty("Download.maxBufferSize")))];
                int bytesRead = 1;
                while (bytesRead > 0) {
                    if (timeoutTime > 0 && timeService.currentTimeMillis() > timeoutTime) {
                        break;
                    }
                    bytesRead = is.read(buffer);
                    if (bytesRead > 0) {
                        bytesReceived = bytesReceived + bytesRead;
                    }
                }
                return new TransferTestResult(0d, bytesReceived, timeService.currentTimeMillis() - startTime);
            }
        } catch (IOException e) {
            throw new ServerRequestException(e);
        }
    }

    public byte[] get(URI uri) {
        Objects.requireNonNull(uri);
        try {
            HttpURLConnection conn = httpClient.createConnection(uri, GET);
            try (InputStream is = conn.getInputStream()) {
                return IOUtils.toByteArray(is);
            }
        } catch (IOException e) {
            throw new ServerRequestException(e);
        }
    }

}
