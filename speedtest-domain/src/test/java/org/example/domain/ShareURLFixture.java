package org.example.domain;

import java.net.URI;

public class ShareURLFixture {

    public static ShareURL some() {
        return new ShareURL(
                URI.create("https://www.speedtest.net/result/19006117741.png"));
    }

}
