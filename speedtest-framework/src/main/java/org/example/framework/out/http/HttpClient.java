package org.example.framework.out.http;

import jakarta.enterprise.context.ApplicationScoped;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Objects;

@ApplicationScoped
public class HttpClient {

    private final Properties properties;

    public HttpClient(Properties properties) {
        this.properties = properties;
    }

    public HttpURLConnection createConnection(URI uri, RequestMethod requestMethod) throws IOException {
        Objects.requireNonNull(uri);
        Objects.requireNonNull(requestMethod);
        HttpURLConnection conn = (HttpURLConnection) uri.toURL().openConnection();
        conn.setUseCaches(properties.useCaches());
        conn.setRequestMethod(requestMethod.name());
        conn.setRequestProperty(RequestProperty.USER_AGENT.value(), properties.userAgent());
        conn.setRequestProperty(RequestProperty.CONNECTION.value(), properties.connection());
        conn.setRequestProperty(RequestProperty.CACHE_CONTROL.value(), properties.cacheControl());
        return conn;
    }

}
