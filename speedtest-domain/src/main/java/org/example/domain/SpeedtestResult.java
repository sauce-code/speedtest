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
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        SpeedtestResult that = (SpeedtestResult) object;
        return Objects.equals(startTime, that.startTime) && Objects.equals(endTime, that.endTime) && Objects.equals(client, that.client) && Objects.equals(server, that.server) && Objects.equals(latency, that.latency) && Objects.equals(download, that.download) && Objects.equals(upload, that.upload) && Objects.equals(shareUrl, that.shareUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime, client, server, latency, download, upload, shareUrl);
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
