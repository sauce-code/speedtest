package org.example;

import org.example.application.in.SpeedtestApplicationCommand;
import org.example.application.in.SpeedtestApplicationService;
import org.example.application.out.model.DistanceUnit;
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

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) {
        SpeedtestApplicationService speedtestApplicationService = init();
        DistanceUnit distanceUnit = DistanceUnit.KILOMETER;
        SpeedtestApplicationCommand command = new SpeedtestApplicationCommand(distanceUnit);
        SpeedtestResult speedtestResult = speedtestApplicationService.run(command);
        System.out.println(speedtestResult);
    }

    private static SpeedtestApplicationService init() {

        HttpClient httpClient = new HttpClient();
        HttpGetClient httpGetClient = new HttpGetClient(httpClient);
        HttpPostClient httpPostClient = new HttpPostClient(httpClient);
        TransferService transferService = new TransferService();

        SpeedtestResultIDService speedtestResultIDService = new SpeedtestResultIDService();
        TimeServiceImpl timeService = new TimeServiceImpl();
        ConfigServiceImpl configService = new ConfigServiceImpl(httpGetClient);
        ServerServiceImpl serverService = new ServerServiceImpl(httpGetClient);
        LatencyServiceImpl latencyService = new LatencyServiceImpl(httpGetClient);
        DownloadServiceImpl downloadService = new DownloadServiceImpl(httpGetClient, transferService);
        UploadServiceImpl uploadService = new UploadServiceImpl(httpPostClient, transferService);
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