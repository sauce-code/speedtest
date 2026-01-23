package org.example.domain;

import org.example.structure.Entity;

import java.time.LocalDateTime;

public class SpeedtestResult extends Entity<SpeedtestResultID> {

    public SpeedtestResult(
            SpeedtestResultID id,
            LocalDateTime startTime,
            LocalDateTime endTime,
            Client client,
            Server server,
            LatencyTestResult latency,
            TransferTestResult download,
            TransferTestResult upload,
            String shareUrl) {
        super(id);
        this.startTime = startTime;
        this.endTime = endTime;
        this.client = client;
        this.server = server;
        this.latency = latency;
        this.download = download;
        this.upload = upload;
        this.shareUrl = shareUrl;
    }

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Client client;
    private Server server;
    private LatencyTestResult latency;
    private TransferTestResult download;
    private TransferTestResult upload;
    private String shareUrl;

    @Override
    public String toString() {
        return "SpeedtestResult{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", client=" + client +
                ", domainServer=" + server +
                ", latency=" + latency +
                ", download=" + download +
                ", upload=" + upload +
                ", shareUrl='" + shareUrl + '\'' +
                '}';
    }

}
