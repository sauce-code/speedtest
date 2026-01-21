package org.example.framework.out.http;

import org.apache.commons.io.IOUtils;
import org.example.domain.TransferTestResult;
import org.example.framework.out.Util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public final class HttpPostClient {

    public static final String CONTENT_LENGTH = "Content-Length";
    private static final String POST = "POST";

    private final HttpClient httpClient;

    public HttpPostClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public TransferTestResult partialPostUploadData(String urlString, long timeoutTime, String dataString) {
        Objects.requireNonNull(urlString);
        Objects.requireNonNull(dataString);
        final int maxBufferSize = Integer.parseInt(Objects.requireNonNull(Util.getConfigProperty("Upload.maxBufferSize")));
        int bytesSent = 0;
        try (InputStream is = new ByteArrayInputStream(dataString.getBytes())) {
            final HttpURLConnection conn = httpClient.createConnection(new URL(urlString), POST);
            conn.setChunkedStreamingMode(maxBufferSize);
            conn.setDoOutput(true);
            conn.setRequestProperty(CONTENT_LENGTH, Integer.toString(dataString.length()));
            final long startTime = System.currentTimeMillis();
            final DataOutputStream dos = new DataOutputStream(conn.getOutputStream());

            int bytesAvailable = is.available();
            int bufferSize = Math.min(bytesAvailable, maxBufferSize);
            final byte[] buffer = new byte[bufferSize];
            int bytesRead = 1;
            while (bytesRead > 0) {
                if (timeoutTime > 0 && System.currentTimeMillis() > timeoutTime) {
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
            return new TransferTestResult(0d, bytesSent, System.currentTimeMillis() - startTime);
        } catch (IOException e) {
            throw new ServerRequestException(e);
        }
    }

    public String postBodyWithSharedData(String urlString, String encodedBody) {
        Objects.requireNonNull(urlString);
        Objects.requireNonNull(encodedBody);
        try {
            final HttpURLConnection conn = httpClient.createConnection(new URL(urlString), POST);
            conn.setDoOutput(true);
            conn.setRequestProperty(CONTENT_LENGTH, Integer.toString(encodedBody.length()));
            conn.setRequestProperty("Referer", "http://c.speedtest.net/flash/speedtest.swf");
            conn.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            try (OutputStream os = conn.getOutputStream();
                 BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8))) {
                writer.write(encodedBody);
                writer.flush();
                try (InputStream is = conn.getInputStream()) {
                    return IOUtils.toString(is, StandardCharsets.UTF_8);
                }
            }
        } catch (IOException e) {
            throw new ServerRequestException(e);
        }
    }

}
