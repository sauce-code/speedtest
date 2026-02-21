package org.example.framework.out.http;

import jakarta.inject.Singleton;
import org.example.domain.TransferTestResult;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.util.Objects;

@Singleton
public class HttpPostClient {

    private final Properties properties;
    private final HttpClient httpClient;
    private final Clock clock;

    public HttpPostClient(
            Properties properties,
            HttpClient httpClient,
            Clock clock) {
        this.properties = properties;
        this.httpClient = httpClient;
        this.clock = clock;
    }

    public TransferTestResult partialPostUploadData(URI uri, long timeoutTime, String dataString) throws ServerRequestException {
        Objects.requireNonNull(uri);
        Objects.requireNonNull(dataString);
        int maxBufferSize = properties.upload().maxBufferSize();
        int bytesSent = 0;
        try (InputStream is = new ByteArrayInputStream(dataString.getBytes())) {
            HttpURLConnection conn = httpClient.createConnection(uri, RequestMethod.POST);
            conn.setChunkedStreamingMode(maxBufferSize);
            conn.setDoOutput(true);
            conn.setRequestProperty(RequestProperty.CONTENT_LENGTH.value(), Integer.toString(dataString.length()));
            long startTime = clock.millis();
            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
            int bytesAvailable = is.available();
            int bufferSize = Math.min(bytesAvailable, maxBufferSize);
            byte[] buffer = new byte[bufferSize];
            int bytesRead = 1;
            while (bytesRead > 0) {
                if (timeoutTime > 0 && clock.millis() > timeoutTime) {
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
            var duartionInMs = clock.millis() - startTime;
            dos.flush();
            dos.close();
            return new TransferTestResult(bytesSent, duartionInMs);
        } catch (IOException e) {
            throw new ServerRequestException(e);
        }
    }

    public String postBodyWithSharedData(URI uri, String encodedBody) throws ServerRequestException {
        Objects.requireNonNull(uri);
        Objects.requireNonNull(encodedBody);
        try {
            HttpURLConnection conn = httpClient.createConnection(uri, RequestMethod.POST);
            conn.setDoOutput(true);
            conn.setRequestProperty(RequestProperty.CONTENT_LENGTH.value(), Integer.toString(encodedBody.length()));
            conn.setRequestProperty(RequestProperty.REFERER.value(), properties.upload().referer());
            conn.addRequestProperty(RequestProperty.CONTENT_TYPE.value(), properties.upload().contentType());
            try (OutputStream os = conn.getOutputStream();
                 OutputStreamWriter osw = new OutputStreamWriter(os, StandardCharsets.UTF_8);
                 BufferedWriter writer = new BufferedWriter(osw)) {
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
