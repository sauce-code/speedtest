package org.example.framework.out.http;

import org.apache.commons.io.IOUtils;
import org.example.domain.TransferTestResult;
import org.example.framework.out.Util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class HttpGetClient {

    private static final String GET = "GET";

    private final HttpClient httpClient;

    public HttpGetClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public TransferTestResult partialGetDownloadData(URL url, long timeoutTime) {
        Objects.requireNonNull(url);
        int bytesReceived = 0;
        try {
            HttpURLConnection conn = httpClient.createConnection(url, GET);
            long startTime = System.currentTimeMillis();
            try (InputStream is = conn.getInputStream()) {
                byte[] buffer =
                        new byte[Integer.parseInt(Objects.requireNonNull(Util.getConfigProperty("Download.maxBufferSize")))];
                int bytesRead = 1;
                while (bytesRead > 0) {
                    if (timeoutTime > 0 && System.currentTimeMillis() > timeoutTime) {
                        break;
                    }
                    bytesRead = is.read(buffer);
                    if (bytesRead > 0) {
                        bytesReceived = bytesReceived + bytesRead;
                    }
                }
                return new TransferTestResult(0d, bytesReceived, System.currentTimeMillis() - startTime);
            }
        } catch (IOException e) {
            throw new ServerRequestException(e);
        }
    }

    public byte[] get(URL url) {
        Objects.requireNonNull(url);
        try {
            HttpURLConnection conn = httpClient.createConnection(url, GET);
            try (InputStream is = conn.getInputStream()) {
                return IOUtils.toByteArray(is);
            }
        } catch (IOException e) {
            throw new ServerRequestException(e);
        }
    }

}
