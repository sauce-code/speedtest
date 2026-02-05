package org.example.application.out;

import org.example.domain.ShareURL;

public interface ShareUrlService {

    ShareURL createShareUrl(int serverId, double latency, double uploadMbps, double downloadMbps);

}
