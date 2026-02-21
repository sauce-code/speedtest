package org.example.application.in;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.application.out.*;
import org.example.domain.*;
import org.example.domain.config.Config;

import java.io.File;
import java.time.Clock;
import java.time.Instant;
import java.util.List;

@ApplicationScoped
public class SpeedtestApplicationService {

    private final Logger logger;
    private final LockService lockService;
    private final IDService<SpeedtestResultID> idService;
    private final Clock clock;
    private final ConfigService configService;
    private final ServerService serverService;
    private final LatencyService latencyService;
    private final DownloadService downloadService;
    private final UploadService uploadService;
    private final ShareUrlService shareUrlService;
    private final ImageStore imageStore;
    private final Repository<SpeedtestResultID, SpeedtestResult> repository;

    public SpeedtestApplicationService(
            Logger logger,
            LockService lockService,
            IDService<SpeedtestResultID> idService,
            Clock clock,
            ConfigService configService,
            ServerService serverService,
            LatencyService latencyService,
            DownloadService downloadService,
            UploadService uploadService,
            ShareUrlService shareUrlService,
            ImageStore imageStore,
            Repository<SpeedtestResultID, SpeedtestResult> repository) {
        this.logger = logger;
        this.lockService = lockService;
        this.idService = idService;
        this.clock = clock;
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
            var message = "Application is already busy.";
            logger.error(message);
            throw new ApplicationLockException(message);
        }
        try {
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

            logger.info("calculating closest servers ...");
            List<ServerDistanceResult> closestServers = config.client().closestServers(
                    servers);

            logger.info("requesting fastest server ...");
            ServerLatencyResult fastestServer = latencyService.getFastestServer(
                    closestServers);
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
                    uploadResult.rateInMbps(),
                    downloadResult.rateInMbps());
            logger.info(shareUrl);

            Instant endTime = clock.instant();
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
            File file = imageStore.store(shareUrl);
            logger.info(file);

            logger.info("saving csv ...");
            repository.create(speedtestResult);

            return speedtestResult;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ApplicationRunException(e);
        } finally {
            logger.info("resetting lock ...");
            lockService.reset();
        }
    }

}
