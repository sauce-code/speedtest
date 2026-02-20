package org.example.domain;

import org.example.structure.Entity;

import java.time.Instant;
import java.util.Objects;

public class SpeedtestResult extends Entity<SpeedtestResultID> {

    private final Instant startTime;
    private final Instant endTime;
    private final Client client;
    private final Server server;
    private final LatencyTestResult latency;
    private final TransferTestResult download;
    private final TransferTestResult upload;
    private final ShareURL shareUrl;

    public SpeedtestResult(
            SpeedtestResultID id,
            Instant startTime,
            Instant endTime,
            Client client,
            Server server,
            LatencyTestResult latency,
            TransferTestResult download,
            TransferTestResult upload,
            ShareURL shareUrl) {
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

    public Instant startTime() {
        return startTime;
    }

    public Instant endTime() {
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

    public ShareURL shareUrl() {
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
