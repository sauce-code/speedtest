package org.example.framework.out.latency;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.application.out.LatencyService;
import org.example.application.out.Logger;
import org.example.application.out.ServerLatencyResult;
import org.example.domain.Latency;
import org.example.domain.LatencyTestResult;
import org.example.domain.ServerDistance;
import org.example.framework.out.http.HttpGetClient;
import org.example.util.Objectz;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.util.*;

@ApplicationScoped
public class LatencyServiceImpl implements LatencyService {

    private static final String TEST_FILE = "/latency.txt?x=";
    private static final String EXPECTED_BODY = "test=test\n";

    private final Logger logger;
    private final Properties properties;
    private final HttpGetClient httpGetClient;
    private final Clock clock;

    public LatencyServiceImpl(
            Logger logger,
            Properties properties,
            HttpGetClient httpGetClient,
            Clock clock) {
        this.logger = logger;
        this.properties = properties;
        this.httpGetClient = httpGetClient;
        this.clock = clock;
    }

    @Override
    public ServerLatencyResult getFastestServer(List<ServerDistance> serverDistances) {
        Objects.requireNonNull(serverDistances);
        Objectz.require(!serverDistances.isEmpty());
        return serverDistances.stream()
                .sorted(Comparator.comparing(ServerDistance::distance))
                .limit(properties.limit())
                .map(this::latencyTestResult)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .min(Comparator.comparing(e -> e.latencyTestResult().latency()))
                .orElseThrow(() -> new LatencyServiceException("Could not receive any latency."));
    }

    private Optional<ServerLatencyResult> latencyTestResult(ServerDistance serverDistance) {
        Objects.requireNonNull(serverDistance);
        var server = serverDistance.server();
        var latencies = testLatency(server.uri());
        var average = average(latencies);
        if (average.isEmpty()) {
            logger.warnv("Could not get any latency for host: {0}", serverDistance.server().host());
            return Optional.empty();
        }
        var latency = Latency.valueOf(average.getAsDouble());
        var distance = serverDistance.distance();
        var latencyTestResult = new LatencyTestResult(latency, distance);
        var fastestServerResult = new ServerLatencyResult(server, latencyTestResult);
        return Optional.of(fastestServerResult);
    }

    private OptionalDouble average(List<Long> latencies) {
        Objects.requireNonNull(latencies);
        return latencies.stream()
                .mapToLong(Long::longValue)
                .average();
    }

    private List<Long> testLatency(URI serverUrl) {
        Objects.requireNonNull(serverUrl);
        List<Long> latencies = new ArrayList<>();
        for (int i = 0; i < properties.testsPerServer(); i++) {
            try {
                String testUrlString = serverUrl + TEST_FILE + clock.millis();
                URI uri = URI.create(testUrlString);
                long startTimestamp = clock.millis();
                byte[] bytes = httpGetClient.get(uri);
                long totalTime = clock.millis() - startTimestamp;
                if (new String(bytes, StandardCharsets.UTF_8).equals(EXPECTED_BODY)) {
                    latencies.add(totalTime / 2);
                } else {
                    logger.warnv("An error occurred while pinging {0}: Body did not match.", serverUrl);
                }
            } catch (Exception e) {
                logger.warnv("An error occurred while pinging {0}: {1}", serverUrl, e.getMessage());
            }
        }
        return latencies;
    }

}
