package org.example.application.in;

import org.example.application.out.*;
import org.example.domain.*;
import org.example.domain.config.ConfigFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RunApplicationServiceTest {

    RunApplicationService runApplicationService;
    Logger logger;
    IDService<SpeedtestResultID> idService;
    Clock clock;
    ConfigService configService;
    ServerService serverService;
    LatencyService latencyService;
    DownloadService downloadService;
    UploadService uploadService;
    ShareUrlService shareUrlService;

    @BeforeEach
    @SuppressWarnings("unchecked")
    void setUp() {
        logger = mock(Logger.class);
        idService = mock(IDService.class);
        clock = mock(Clock.class);
        configService = mock(ConfigService.class);
        serverService = mock(ServerService.class);
        latencyService = mock(LatencyService.class);
        downloadService = mock(DownloadService.class);
        uploadService = mock(UploadService.class);
        shareUrlService = mock(ShareUrlService.class);
        runApplicationService = new RunApplicationService(
                logger,
                idService,
                clock,
                configService,
                serverService,
                latencyService,
                downloadService,
                uploadService,
                shareUrlService);
    }

    @Test
    void whenNoErrors_thenResult() {
        when(idService.create())
                .thenReturn(SpeedtestResultIDFixture.some());
        when(clock.instant())
                .thenReturn(SpeedtestResultFixture.some().startTime())
                .thenReturn(SpeedtestResultFixture.some().endTime());
        when(configService.config())
                .thenReturn(ConfigFixture.some());
        when(serverService.servers(ConfigFixture.some().downloadSettings().threadsPerUrl()))
                .thenReturn(ServerFixture.some());
        when(latencyService.getFastestServer(ServerDistanceFixture.some()))
                .thenReturn(ServerLatencyResultFixture.some());
        when(downloadService.testDownload(ServerFixture.some().get(25), ConfigFixture.some().downloadSettings()))
                .thenReturn(TransferTestResultFixture.download());
        when(uploadService.testUpload(ServerFixture.some().get(25), ConfigFixture.some().uploadSettings()))
                .thenReturn(TransferTestResultFixture.upload());
        when(shareUrlService.createShareUrl(
                ServerFixture.some().get(25).id(),
                LatencyTestResultFixture.some().latency(),
                TransferTestResultFixture.download().rateInMbps(),
                TransferTestResultFixture.upload().rateInMbps()))
                .thenReturn(ShareURLFixture.some());
        var actual = runApplicationService.run();
        var expected = SpeedtestResultFixture.some();
        assertEquals(expected, actual);
    }

}
