package org.example.domain;

import org.example.structure.Entity;

import java.time.LocalDateTime;

public class SpeedtestResult extends Entity<SpeedtestResultID> {

    public SpeedtestResult(SpeedtestResultID id,
                           LocalDateTime startTime,
                           LocalDateTime endTime,
                           Client client,
                           DomainServer domainServer,
                           LatencyTestResult latency,
                           TransferTestResult download,
                           TransferTestResult upload,
                           String shareUrl) {
        super(id);
        this.startTime = startTime;
        this.endTime = endTime;
        this.client = client;
        this.domainServer = domainServer;
        this.latency = latency;
        this.download = download;
        this.upload = upload;
        this.shareUrl = shareUrl;
    }

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Client client;
    private DomainServer domainServer;
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
                ", domainServer=" + domainServer +
                ", latency=" + latency +
                ", download=" + download +
                ", upload=" + upload +
                ", shareUrl='" + shareUrl + '\'' +
                '}';
    }

}
