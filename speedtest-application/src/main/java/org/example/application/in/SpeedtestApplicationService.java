package org.example.application.in;

import org.example.application.out.*;
import org.example.application.out.model.Config;
import org.example.application.out.model.ConfigService;
import org.example.domain.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SpeedtestApplicationService {

    private final IDService<SpeedtestResultID> idService;
    private final TimeService timeService;
    private final ConfigService configService;
    private final ServerService serverService;
    private final LatencyService latencyService;
    private final DownloadService downloadService;
    private final UploadService uploadService;
    private final ShareUrlService shareUrlService;

    public SpeedtestApplicationService(
            IDService<SpeedtestResultID> idService,
            TimeService timeService,
            ConfigService configService,
            ServerService serverService,
            LatencyService latencyService,
            DownloadService downloadService,
            UploadService uploadService,
            ShareUrlService shareUrlService) {
        this.idService = idService;
        this.timeService = timeService;
        this.configService = configService;
        this.serverService = serverService;
        this.latencyService = latencyService;
        this.downloadService = downloadService;
        this.uploadService = uploadService;
        this.shareUrlService = shareUrlService;
    }

    public SpeedtestResult run(SpeedtestApplicationCommand command) {
        Objects.requireNonNull(command);
        try {
            SpeedtestResultID id = idService.create();
            LocalDateTime startTime = timeService.localDateTime();
            Config config = configService.config();
            List<DomainServer> domainServers = serverService.servers(
                    config.download().threadsPerUrl());
            Map<Double, DomainServer> closestServers = serverService.findClosestServers(
                    config.client().lat(),
                    config.client().lon(),
                    10,
                    command.distanceUnit(),
                    domainServers);
            Map.Entry<DomainServer, LatencyTestResult> fastestServer = latencyService.getFastestServer(closestServers);
            TransferTestResult downloadResult = downloadService.testDownload(
                    fastestServer.getKey().url(),
                    config.download());
            TransferTestResult uploadResult = uploadService.testUpload(
                    fastestServer.getKey().url(),
                    config.upload(),
                    (downloadResult != null && downloadResult.rateInMbps() > 0.1)
                            ? 8
                            : config.upload().threads());
            String shareUrl = shareUrlService.createShareUrl(
                    fastestServer.getKey().id(),
                    fastestServer.getValue().latency(),
                    uploadResult.rateInMbps(),
                    downloadResult.rateInMbps());
            LocalDateTime endTime = timeService.localDateTime();
            return new SpeedtestResult(
                    id,
                    startTime,
                    endTime,
                    config.client(),
                    fastestServer.getKey(),
                    fastestServer.getValue(),
                    downloadResult,
                    uploadResult,
                    shareUrl);
        } catch (Exception e) {
            // TODO log

            throw new RuntimeException(e);
        }
    }

}
