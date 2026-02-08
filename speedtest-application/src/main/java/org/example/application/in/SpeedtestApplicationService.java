package org.example.application.in;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.application.out.*;
import org.example.domain.*;
import org.example.domain.config.Config;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class SpeedtestApplicationService {

    private static final Logger logger = LogManager.getLogger();

    private final LockService lockService;
    private final IDService<SpeedtestResultID> idService;
    private final TimeService timeService;
    private final ConfigService configService;
    private final ServerService serverService;
    private final LatencyService latencyService;
    private final DownloadService downloadService;
    private final UploadService uploadService;
    private final ShareUrlService shareUrlService;
    private final ImageStore imageStore;
    private final Repository<SpeedtestResultID, SpeedtestResult> repository;

    @Inject
    public SpeedtestApplicationService(
            LockService lockService,
            IDService<SpeedtestResultID> idService,
            TimeService timeService,
            ConfigService configService,
            ServerService serverService,
            LatencyService latencyService,
            DownloadService downloadService,
            UploadService uploadService,
            ShareUrlService shareUrlService,
            ImageStore imageStore,
            Repository<SpeedtestResultID, SpeedtestResult> repository) {
        this.lockService = lockService;
        this.idService = idService;
        this.timeService = timeService;
        this.configService = configService;
        this.serverService = serverService;
        this.latencyService = latencyService;
        this.downloadService = downloadService;
        this.uploadService = uploadService;
        this.shareUrlService = shareUrlService;
        this.imageStore = imageStore;
        this.repository = repository;
    }

    public SpeedtestResult run() {
        logger.info("setting lock ...");
        if (!lockService.setBusy()) {
            logger.error("application is already busy");
            throw new RuntimeException("application is already Busy");
        }
        try {
            SpeedtestResultID id = idService.create();
            LocalDateTime startTime = timeService.localDateTime();

            logger.info("requesting config ...");
            Config config = configService.config();
            logger.info(config);

            logger.info("requesting servers ...");
            List<Server> servers = serverService.servers(
                    config.downloadSettings().threadsPerUrl());
            logger.info("fetched {} servers", servers.size());

            logger.info("calculating closest servers ...");
            Map<Distance, Server> closestServers = config.client().closestServers(
                    servers,
                    10);
            logger.info("calculated {} closest servers", closestServers.size());

            logger.info("requesting fastest server ...");
            FastestServerResult fastestServer = latencyService.getFastestServer(
                    closestServers);
            logger.info(fastestServer.server());
            logger.info(fastestServer.latencyTestResult());

            logger.info("testing download ...");
            TransferTestResult downloadResult = downloadService.testDownload(
                    fastestServer.server(),
                    config.downloadSettings());
            logger.info(downloadResult);

            logger.info("testing upload ...");
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

            LocalDateTime endTime = timeService.localDateTime();
            SpeedtestResult speedtestResult = new SpeedtestResult(
                    id,
                    startTime,
                    endTime,
                    config.client(),
                    fastestServer.server(),
                    fastestServer.latencyTestResult(),
                    downloadResult,
                    uploadResult,
                    shareUrl);

            logger.info("saving share ...");
            URI uri = imageStore.store(shareUrl);
            logger.info(uri);

            logger.info("saving csv ...");
            repository.create(speedtestResult);

            return speedtestResult;
        } catch (Exception e) {
            logger.error(e);
            throw new RuntimeException(e);
        } finally {
            logger.info("resetting lock ...");
            lockService.reset();
        }
    }

}
