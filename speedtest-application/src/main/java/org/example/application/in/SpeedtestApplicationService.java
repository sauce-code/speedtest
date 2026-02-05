package org.example.application.in;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.application.out.*;
import org.example.domain.*;
import org.example.domain.config.Config;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class SpeedtestApplicationService {

    private static final Logger logger = LogManager.getLogger();

    private final IDService<SpeedtestResultID> idService;
    private final TimeService timeService;
    private final ConfigService configService;
    private final ServerService serverService;
    private final LatencyService latencyService;
    private final DownloadService downloadService;
    private final UploadService uploadService;
    private final ShareUrlService shareUrlService;
    private final ImageStore imageStore;

    public SpeedtestApplicationService(
            IDService<SpeedtestResultID> idService,
            TimeService timeService,
            ConfigService configService,
            ServerService serverService,
            LatencyService latencyService,
            DownloadService downloadService,
            UploadService uploadService,
            ShareUrlService shareUrlService,
            ImageStore imageStore) {
        this.idService = idService;
        this.timeService = timeService;
        this.configService = configService;
        this.serverService = serverService;
        this.latencyService = latencyService;
        this.downloadService = downloadService;
        this.uploadService = uploadService;
        this.shareUrlService = shareUrlService;
        this.imageStore = imageStore;
    }

    public SpeedtestResult run() {
        try {
            SpeedtestResultID id = idService.create();
            LocalDateTime startTime = timeService.localDateTime();

            logger.info("Requesting config ...");
            Config config = configService.config();
            logger.info(config);

            logger.info("Requesting servers ...");
            List<Server> servers = serverService.servers(
                    config.downloadSettings().threadsPerUrl());
            logger.info("Fetched {} servers", servers.size());

            logger.info("Calculating closest servers ...");
            Map<Distance, Server> closestServers = config.client().closestServers(
                    servers,
                    10);
            logger.info("Calculated {} closest servers", closestServers.size());

            logger.info("Requesting fastest server ...");
            FastestServerResult fastestServer = latencyService.getFastestServer(
                    closestServers);
            logger.info(fastestServer.server());
            logger.info(fastestServer.latencyTestResult());

            logger.info("Testing download ...");
            TransferTestResult downloadResult = downloadService.testDownload(
                    fastestServer.server(),
                    config.downloadSettings());
            logger.info(downloadResult);

            logger.info("Testing upload ...");
            TransferTestResult uploadResult = uploadService.testUpload(
                    fastestServer.server(),
                    config.uploadSettings());
            logger.info(uploadResult);

            logger.info("creating share url ...");
            ShareURL shareUrl = shareUrlService.createShareUrl(
                    fastestServer.server().id(),
                    fastestServer.latencyTestResult().latency(),
                    uploadResult.rateInMbps(),
                    downloadResult.rateInMbps());
            logger.info(shareUrl);

            logger.info("saving share ...");
            URI uri = imageStore.store(shareUrl);
            logger.info(uri);

            LocalDateTime endTime = timeService.localDateTime();
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
        } catch (Exception e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

}
