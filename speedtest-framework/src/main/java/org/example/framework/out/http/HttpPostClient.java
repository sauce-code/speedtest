package org.example.framework.out.http;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.application.out.TimeService;
import org.example.domain.TransferTestResult;
import org.example.framework.out.Util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@ApplicationScoped
public class HttpPostClient {

    public static final String CONTENT_LENGTH = "Content-Length";

    private final HttpClient httpClient;
    private final TimeService timeService;

    public HttpPostClient(HttpClient httpClient, TimeService timeService) {
        this.httpClient = httpClient;
        this.timeService = timeService;
    }

    public TransferTestResult partialPostUploadData(URI uri, long timeoutTime, String dataString) {
        Objects.requireNonNull(uri);
        Objects.requireNonNull(dataString);
        final int maxBufferSize = Integer.parseInt(Objects.requireNonNull(Util.getConfigProperty("Upload.maxBufferSize")));
        int bytesSent = 0;
        try (InputStream is = new ByteArrayInputStream(dataString.getBytes())) {
            HttpURLConnection conn = httpClient.createConnection(uri, RequestMethod.POST);
            conn.setChunkedStreamingMode(maxBufferSize);
            conn.setDoOutput(true);
            conn.setRequestProperty(CONTENT_LENGTH, Integer.toString(dataString.length()));
            long startTime = timeService.currentTimeMillis();
            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());

            int bytesAvailable = is.available();
            int bufferSize = Math.min(bytesAvailable, maxBufferSize);
            byte[] buffer = new byte[bufferSize];
            int bytesRead = 1;
            while (bytesRead > 0) {
                if (timeoutTime > 0 && timeService.currentTimeMillis() > timeoutTime) {
                    break;
                }
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = is.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = is.read(buffer, 0, bufferSize);
                if (bytesRead > 0) {
                    bytesSent = bytesSent + bytesRead;
                }
            }
            dos.flush();
            dos.close();
            return new TransferTestResult(0d, bytesSent, timeService.currentTimeMillis() - startTime);
        } catch (IOException e) {
            throw new ServerRequestException(e);
        }
    }

    public String postBodyWithSharedData(URI uri, String encodedBody) {
        Objects.requireNonNull(uri);
        Objects.requireNonNull(encodedBody);
        try {
            final HttpURLConnection conn = httpClient.createConnection(uri, RequestMethod.POST);
            conn.setDoOutput(true);
            conn.setRequestProperty(CONTENT_LENGTH, Integer.toString(encodedBody.length()));
            conn.setRequestProperty("Referer", "http://c.speedtest.net/flash/speedtest.swf");
            conn.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            try (OutputStream os = conn.getOutputStream();
                 BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8))) {
                writer.write(encodedBody);
                writer.flush();
                try (InputStream is = conn.getInputStream()) {
                    return new String(is.readAllBytes(), StandardCharsets.UTF_8);
                }
            }
        } catch (IOException e) {
            throw new ServerRequestException(e);
        }
    }

}
