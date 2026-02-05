package org.example.domain;

import org.example.structure.Entity;

import java.time.LocalDateTime;
import java.util.Objects;

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
        this.startTime = Objects.requireNonNull(startTime);
        this.endTime = Objects.requireNonNull(endTime);
        this.client = Objects.requireNonNull(client);
        this.server = Objects.requireNonNull(server);
        this.latency = Objects.requireNonNull(latency);
        this.download = Objects.requireNonNull(download);
        this.upload = Objects.requireNonNull(upload);
        this.shareUrl = Objects.requireNonNull(shareUrl);
    }

    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final Client client;
    private final Server server;
    private final LatencyTestResult latency;
    private final TransferTestResult download;
    private final TransferTestResult upload;
    private final String shareUrl;

    public LocalDateTime startTime() {
        return startTime;
    }

    public LocalDateTime endTime() {
        return endTime;
    }

    public Client client() {
        return client;
    }

    public Server server() {
        return server;
    }

    public LatencyTestResult latency() {
        return latency;
    }

    public TransferTestResult download() {
        return download;
    }

    public TransferTestResult upload() {
        return upload;
    }

    public String shareUrl() {
        return shareUrl;
    }

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
