package org.example.framework.out.http;

public enum RequestProperty {
    USER_AGENT("User-Agent"),
    CONNECTION("Connection"),
    CACHE_CONTROL("Cache-Control"),
    CONTENT_LENGTH("Content-Length"),
    REFERER("Referer"),
    CONTENT_TYPE("Content-Type");

    private final String value;

    RequestProperty(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

}
