package org.example.application.out;

import org.example.domain.Latency;
import org.example.domain.ShareURL;

public interface ShareUrlService {

    ShareURL createShareUrl(int serverId, Latency latency, double uploadMbps, double downloadMbps);

}
