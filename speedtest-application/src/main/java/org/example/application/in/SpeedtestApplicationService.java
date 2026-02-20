package org.example.application.in;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.application.out.*;
import org.example.domain.*;
import org.example.domain.config.Config;
import org.example.domain.location.Distance;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.TreeMap;

@ApplicationScoped
public class SpeedtestApplicationService {

    private final Logger logger;
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

    public SpeedtestApplicationService(
            Logger logger,
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
        this.logger = logger;
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
            var message = "Application is already busy.";
            logger.error(message);
            throw new ApplicationLockException(message);
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
            logger.infov("fetched {0} servers", servers.size());

            logger.info("calculating closest servers ...");
            TreeMap<Distance, Server> closestServers = config.client().closestServers(
                    servers);
            var closestServersLimited =  serverService.limit(closestServers);
            logger.infov("calculated {0} closest servers", closestServersLimited.size());

            logger.info("requesting fastest server ...");
            FastestServerResult fastestServer = latencyService.getFastestServer(
                    closestServersLimited);
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
            File file = imageStore.store(shareUrl);
            logger.info(file);

            logger.info("saving csv ...");
            repository.create(speedtestResult);

            return speedtestResult;
        } catch (Exception e) {
            logger.error(e);
            logger.error(e.getMessage());
            throw new ApplicationRunException(e);
        } finally {
            logger.info("resetting lock ...");
            lockService.reset();
        }
    }

}
