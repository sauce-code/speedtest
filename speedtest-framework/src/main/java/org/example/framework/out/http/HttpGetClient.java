package org.example.framework.out.http;

import org.apache.commons.io.IOUtils;
import org.example.domain.TransferTestResult;
import org.example.framework.out.Util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class HttpGetClient extends AbstractHttpClient {

    private static final String GET = "GET";

    public TransferTestResult partialGetDownloadData(String urlString, long timeoutTime) {
        if (urlString != null) {
            int bytesReceived = 0;
            try {
                HttpURLConnection conn = createConnection(new URL(urlString), GET);
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
        } else {
            throw new IllegalArgumentException();
        }
    }

    public byte[] get(final String urlString) throws ServerRequestException {
        if (urlString != null) {
            try {
                HttpURLConnection conn = createConnection(new URL(urlString), GET);
                try (InputStream is = conn.getInputStream()) {
                    return IOUtils.toByteArray(is);
                }
            } catch (IOException e) {
                throw new ServerRequestException(e);
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

}
