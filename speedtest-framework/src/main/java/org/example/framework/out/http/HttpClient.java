package org.example.framework.out.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class HttpClient {

    public HttpURLConnection createConnection(URL url, String requestMethod) throws IOException {
        Objects.requireNonNull(url);
        Objects.requireNonNull(requestMethod);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setUseCaches(false);
        conn.setRequestMethod(requestMethod);
        conn.setRequestProperty("User-Agent", "speedtest-client");
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("Cache-Control", "no-cache");
        return conn;
    }

}
