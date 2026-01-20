package org.example.framework.out;

import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Util {

    public static String getConfigProperty(final String key) {
        if (key != null) {
            final String resource = "config.properties";
            try (InputStream is = Util.class.getClassLoader().getResourceAsStream(resource)) {
                final Properties prop = new Properties();
                prop.load(is);
                return prop.getProperty(key);
            } catch (Exception e) {
                return null;
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    public static Map<String, String> getQueryParams(final String paramString) {
        if (paramString != null) {
            final Map<String, String> params = new HashMap<>();
            for (String param : paramString.split("&")) {
                final String[] pair = param.split("=");
                final String key = URLDecoder.decode(pair[0], StandardCharsets.UTF_8);
                if (!params.containsKey(key)) {
                    String value = "";
                    if (pair.length > 1) {
                        value = URLDecoder.decode(pair[1], StandardCharsets.UTF_8);
                    }
                    params.put(key, value);
                }
            }
            return params;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public static double calculateMbps(final int bytes, final long timeInMs) {
        return (bytes * 8.0) / (timeInMs * 1000.0);
    }

}
