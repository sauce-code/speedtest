package org.example;

import org.example.application.in.SpeedtestApplicationService;
import org.example.domain.SpeedtestResult;
import org.example.framework.out.config.ConfigServiceImpl;
import org.example.framework.out.http.HttpClient;
import org.example.framework.out.http.HttpGetClient;
import org.example.framework.out.http.HttpPostClient;
import org.example.framework.out.id.SpeedtestResultIDService;
import org.example.framework.out.latency.LatencyServiceImpl;
import org.example.framework.out.server.ServerServiceImpl;
import org.example.framework.out.shareurl.ShareUrlServiceImpl;
import org.example.framework.out.time.TimeServiceImpl;
import org.example.framework.out.transfer.DownloadServiceImpl;
import org.example.framework.out.transfer.TransferService;
import org.example.framework.out.transfer.UploadServiceImpl;

public class Main {

    public static void main(String[] args) {
        SpeedtestApplicationService speedtestApplicationService = init();
        SpeedtestResult speedtestResult = speedtestApplicationService.run();
        System.out.println(speedtestResult);
    }

    private static SpeedtestApplicationService init() {

        HttpClient httpClient = new HttpClient();
        TimeServiceImpl timeService = new TimeServiceImpl();
        HttpGetClient httpGetClient = new HttpGetClient(httpClient, timeService);
        HttpPostClient httpPostClient = new HttpPostClient(httpClient, timeService);
        TransferService transferService = new TransferService();

        SpeedtestResultIDService speedtestResultIDService = new SpeedtestResultIDService();
        ConfigServiceImpl configService = new ConfigServiceImpl(httpGetClient);
        ServerServiceImpl serverService = new ServerServiceImpl(httpGetClient);
        LatencyServiceImpl latencyService = new LatencyServiceImpl(httpGetClient, timeService);
        DownloadServiceImpl downloadService = new DownloadServiceImpl(httpGetClient, transferService, timeService);
        UploadServiceImpl uploadService = new UploadServiceImpl(httpPostClient, transferService, timeService);
        ShareUrlServiceImpl shareUrlService = new ShareUrlServiceImpl(httpPostClient);
        return new SpeedtestApplicationService(
                speedtestResultIDService,
                timeService,
                configService,
                serverService,
                latencyService,
                downloadService,
                uploadService,
                shareUrlService);
    }

}
