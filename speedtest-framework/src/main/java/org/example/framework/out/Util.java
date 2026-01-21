package org.example.framework.out;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

public class Util {

    public static String getConfigProperty(String key) {
        Objects.requireNonNull(key);
        String resource = "config.properties";
        try (InputStream is = Util.class.getClassLoader().getResourceAsStream(resource)) {
            Properties prop = new Properties();
            prop.load(is);
            return prop.getProperty(key);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, String> getQueryParams(String paramString) {
        Objects.requireNonNull(paramString);
        Map<String, String> params = new HashMap<>();
        for (String param : paramString.split("&")) {
            String[] pair = param.split("=");
            String key = URLDecoder.decode(pair[0], StandardCharsets.UTF_8);
            if (!params.containsKey(key)) {
                String value = "";
                if (pair.length > 1) {
                    value = URLDecoder.decode(pair[1], StandardCharsets.UTF_8);
                }
                params.put(key, value);
            }
        }
        return params;
    }

    public static double calculateMbps(int bytes, long timeInMs) {
        return (bytes * 8.0) / (timeInMs * 1000.0);
    }

}
