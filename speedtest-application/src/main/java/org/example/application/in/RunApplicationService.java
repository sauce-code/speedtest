package org.example.application.in;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.application.out.*;
import org.example.domain.*;
import org.example.domain.config.Config;

import java.time.Clock;
import java.time.Instant;
import java.util.List;

@ApplicationScoped
public class RunApplicationService {

    private final Logger logger;
    private final IDService<SpeedtestResultID> idService;
    private final Clock clock;
    private final ConfigService configService;
    private final ServerService serverService;
    private final LatencyService latencyService;
    private final DownloadService downloadService;
    private final UploadService uploadService;
    private final ShareUrlService shareUrlService;

    public RunApplicationService(
            Logger logger,
            IDService<SpeedtestResultID> idService,
            Clock clock,
            ConfigService configService,
            ServerService serverService,
            LatencyService latencyService,
            DownloadService downloadService,
            UploadService uploadService,
            ShareUrlService shareUrlService) {
        this.logger = logger;
        this.idService = idService;
        this.clock = clock;
        this.configService = configService;
        this.serverService = serverService;
        this.latencyService = latencyService;
        this.downloadService = downloadService;
        this.uploadService = uploadService;
        this.shareUrlService = shareUrlService;
    }

    public SpeedtestResult run() {

        SpeedtestResultID id = idService.create();
        Instant startTime = clock.instant();

        logger.info("requesting config ...");
        Config config = configService.config();
        logger.info(config);

        logger.info("requesting servers ...");
        List<Server> servers = serverService.servers(
                config.downloadSettings().threadsPerUrl());
        if (servers.isEmpty()) {
            throw new ApplicationRunException("Could not receive any servers.");
        }
        logger.infov("fetched {0} servers", servers.size());

        logger.info("calculating server distances ...");
        List<ServerDistance> serverDistances = config.client().serverDistances(
                servers);

        logger.info("requesting fastest server ...");
        ServerLatencyResult fastestServer = latencyService.getFastestServer(
                serverDistances);
        logger.info(fastestServer.server());
        logger.info(fastestServer.latencyTestResult());

        logger.info("testing download ...");
        TransferTestResult downloadResult = downloadService.testDownload(
                fastestServer.server(),
                config.downloadSettings());
        logger.infov("Download Rate: {0} Mbit/s", downloadResult.rateInMbps());

        logger.info("testing upload ...");
        TransferTestResult uploadResult = uploadService.testUpload(
                fastestServer.server(),
                config.uploadSettings());
        logger.infov("Upload Rate: {0} Mbit/s", uploadResult.rateInMbps());

        logger.info("creating share url ...");
        ShareURL shareUrl = shareUrlService.createShareUrl(
                fastestServer.server().id(),
                fastestServer.latencyTestResult().latency(),
                downloadResult.rateInMbps(), uploadResult.rateInMbps()
        );
        logger.info(shareUrl);

        Instant endTime = clock.instant();
        return new SpeedtestResult(
                id,
                startTime,
                endTime,
                config.client(),
                fastestServer.server(),
                fastestServer.latencyTestResult(),
                downloadResult,
                uploadResult,
                shareUrl);
    }

}
