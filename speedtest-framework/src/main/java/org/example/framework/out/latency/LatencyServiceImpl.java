package org.example.framework.out.latency;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.application.out.LatencyService;
import org.example.application.out.TimeService;
import org.example.domain.Distance;
import org.example.domain.FastestServerResult;
import org.example.domain.LatencyTestResult;
import org.example.domain.Server;
import org.example.framework.out.Util;
import org.example.framework.out.http.HttpGetClient;
import org.example.framework.out.http.ServerRequestException;
import org.example.util.Objectz;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.*;

@ApplicationScoped
public class LatencyServiceImpl implements LatencyService {

    private static final String TEST_FILE = "/latency.txt?x=";
    private static final String EXPECTED_BODY = "test=test\n";

    private final HttpGetClient httpGetClient;
    private final TimeService timeService;

    public LatencyServiceImpl(HttpGetClient httpGetClient, TimeService timeService) {
        this.httpGetClient = httpGetClient;
        this.timeService = timeService;
    }

    @Override
    public FastestServerResult getFastestServer(Map<Distance, Server> serverMap) {
        Objects.requireNonNull(serverMap);
        return findServerLatencies(serverMap).entrySet().stream()
                .min(Comparator.comparing(entry -> entry.getValue().latency()))
                .map(entry -> new FastestServerResult(entry.getKey(), entry.getValue()))
                .orElseThrow(MissingResultException::new);
    }

    private Map<Server, LatencyTestResult> findServerLatencies(Map<Distance, Server> serverMap) {
        Objects.requireNonNull(serverMap);
        int testsPerServer = Integer.parseInt(Objects.requireNonNull(Util.getConfigProperty("Latency.testsPerServer.maxNumber")));
        Map<Server, LatencyTestResult> results = new HashMap<>();
        for (Map.Entry<Distance, Server> entry : serverMap.entrySet()) {
            try {
                List<Long> longs = testLatency(entry.getValue().uri(), testsPerServer);
                double average = calculateAverage(longs);
                LatencyTestResult latencyTestResult = new LatencyTestResult(average, entry.getKey());
                results.put(entry.getValue(), latencyTestResult);
            } catch (ServerRequestException | MissingResultException e) {

            }
        }
        return results;
    }

    private double calculateAverage(List<Long> list) {
        Objects.requireNonNull(list);
        return list.stream()
                .filter(Objects::nonNull)
                .mapToLong(Long::longValue)
                .average()
                .orElseThrow();
    }

    private List<Long> testLatency(URI serverUrl, int limit) {
        Objects.requireNonNull(serverUrl);
        Objectz.require(limit > 0);
        List<Long> latencies = new ArrayList<>();
        for (int i = 0; i < limit; i++) {
            String testUrlString = serverUrl + TEST_FILE + timeService.currentTimeMillis();
            URI uri = URI.create(testUrlString);
            long startTimestamp = timeService.currentTimeMillis();
            byte[] bytes = httpGetClient.get(uri);
            long totalTime = timeService.currentTimeMillis() - startTimestamp;
            if (new String(bytes, StandardCharsets.UTF_8).equals(EXPECTED_BODY)) {
                latencies.add(totalTime / 2);
            }
        }
        return latencies;
    }

}
