package org.example.framework.out.latency;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.application.out.LatencyService;
import org.example.application.out.Logger;
import org.example.application.out.TimeService;
import org.example.domain.Distance;
import org.example.domain.FastestServerResult;
import org.example.domain.LatencyTestResult;
import org.example.domain.Server;
import org.example.framework.out.http.HttpGetClient;
import org.example.util.Objectz;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.*;

@ApplicationScoped
public class LatencyServiceImpl implements LatencyService {

    private static final String TEST_FILE = "/latency.txt?x=";
    private static final String EXPECTED_BODY = "test=test\n";

    private final Logger logger;
    private final Properties properties;
    private final HttpGetClient httpGetClient;
    private final TimeService timeService;

    public LatencyServiceImpl(
            Logger logger,
            Properties properties,
            HttpGetClient httpGetClient,
            TimeService timeService) {
        this.logger = logger;
        this.properties = properties;
        this.httpGetClient = httpGetClient;
        this.timeService = timeService;
    }

    @Override
    public FastestServerResult getFastestServer(Map<Distance, Server> serverMap) {
        Objects.requireNonNull(serverMap);
        Objectz.require(!serverMap.isEmpty());
        return findServerLatencies(serverMap).entrySet().stream()
                .min(Comparator.comparing(entry -> entry.getValue().latency()))
                .map(entry -> new FastestServerResult(entry.getKey(), entry.getValue()))
                .orElseThrow();
    }

    private Map<Server, LatencyTestResult> findServerLatencies(Map<Distance, Server> serverMap) {
        Objects.requireNonNull(serverMap);
        Objectz.require(!serverMap.isEmpty());
        Map<Server, LatencyTestResult> results = new HashMap<>();
        for (Map.Entry<Distance, Server> entry : serverMap.entrySet()) {
            List<Long> longs = testLatency(entry.getValue().uri());
            double average = calculateAverage(longs);
            LatencyTestResult latencyTestResult = new LatencyTestResult(average, entry.getKey());
            results.put(entry.getValue(), latencyTestResult);
        }
        return results;
    }

    private double calculateAverage(List<Long> list) {
        Objects.requireNonNull(list);
        Objectz.require(!list.isEmpty());
        return list.stream()
                .mapToLong(Long::longValue)
                .average()
                .orElseThrow();
    }

    private List<Long> testLatency(URI serverUrl) {
        Objects.requireNonNull(serverUrl);
        List<Long> latencies = new ArrayList<>();
        for (int i = 0; i < properties.testsPerServer(); i++) {
            try {
                String testUrlString = serverUrl + TEST_FILE + timeService.currentTimeMillis(); // TODO use nanos
                URI uri = URI.create(testUrlString);
                long startTimestamp = timeService.currentTimeMillis();
                byte[] bytes = httpGetClient.get(uri);
                long totalTime = timeService.currentTimeMillis() - startTimestamp;
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
