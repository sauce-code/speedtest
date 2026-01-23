package org.example.application.out;

public interface ShareUrlService {

    String createShareUrl(int serverId, double latency, double uploadMbps, double downloadMbps);

}
