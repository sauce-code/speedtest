package org.example.framework.out.http;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.application.out.TimeService;
import org.example.domain.TransferTestResult;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Objects;

@ApplicationScoped
public class HttpGetClient {

    private final Properties properties;
    private final HttpClient httpClient;
    private final TimeService timeService;

    public HttpGetClient(
            Properties properties,
            HttpClient httpClient,
            TimeService timeService) {
        this.properties = properties;
        this.httpClient = httpClient;
        this.timeService = timeService;
    }

    public TransferTestResult partialGetDownloadData(URI uri, long timeoutTime) throws ServerRequestException {
        Objects.requireNonNull(uri);
        int bytesReceived = 0;
        try {
            HttpURLConnection conn = httpClient.createConnection(uri, RequestMethod.GET);
            long startTime = timeService.currentTimeMillis();
            try (InputStream is = conn.getInputStream()) {
                byte[] buffer = new byte[properties.download().maxBufferSize()];
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
                return new TransferTestResult(bytesReceived, timeService.currentTimeMillis() - startTime);
            }
        } catch (IOException e) {
            throw new ServerRequestException(e);
        }
    }

    public byte[] get(URI uri) throws ServerRequestException {
        Objects.requireNonNull(uri);
        try {
            HttpURLConnection conn = httpClient.createConnection(uri, RequestMethod.GET);
            try (InputStream is = conn.getInputStream()) {
                return is.readAllBytes();
            }
        } catch (IOException e) {
            throw new ServerRequestException(e);
        }
    }

}
