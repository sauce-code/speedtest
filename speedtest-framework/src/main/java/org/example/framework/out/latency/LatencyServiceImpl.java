package org.example.framework.out.latency;

import org.example.application.out.LatencyService;
import org.example.domain.Distance;
import org.example.domain.Server;
import org.example.domain.LatencyTestResult;
import org.example.framework.out.Util;
import org.example.framework.out.http.HttpGetClient;
import org.example.framework.out.http.ServerRequestException;
import org.example.util.Objectz;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class LatencyServiceImpl implements LatencyService {

    private static final String TEST_FILE = "/latency.txt?x=";
    private static final String EXPECTED_BODY = "test=test\n";

    private final HttpGetClient httpGetClient;

    public LatencyServiceImpl(HttpGetClient httpGetClient) {
        this.httpGetClient = httpGetClient;
    }

    @Override
    public Map.Entry<Server, LatencyTestResult> getFastestServer(Map<Distance, Server> serverMap) {
        Objects.requireNonNull(serverMap);
        return findServerLatencies(serverMap).entrySet().stream()
                .min(Comparator.comparing(entry -> entry.getValue().latency()))
                .orElseThrow(MissingResultException::new);
    }

    private Map<Server, LatencyTestResult> findServerLatencies(Map<Distance, Server> serverMap) {
        Objects.requireNonNull(serverMap);
        int testsPerServer = Integer.parseInt(Objects.requireNonNull(Util.getConfigProperty("Latency.testsPerServer.maxNumber")));
        Map<Server, LatencyTestResult> results = new HashMap<>();
        for (Map.Entry<Distance, Server> entry : serverMap.entrySet()) {
            try {
                results.put(entry.getValue(), new LatencyTestResult(calculateAverage(
                        testLatency(entry.getValue().url(), testsPerServer)), entry.getKey()));
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

    private List<Long> testLatency(URL serverUrl, int limit) {
        Objects.requireNonNull(serverUrl);
        Objectz.require(limit > 0);
        List<Long> latencies = new ArrayList<>();
        for (int i = 0; i < limit; i++) {
            String testUrl = serverUrl + TEST_FILE + System.currentTimeMillis();
            long startTimestamp = System.currentTimeMillis();
            byte[] bytes = httpGetClient.get(testUrl);
            long totalTime = System.currentTimeMillis() - startTimestamp;
            if (new String(bytes, StandardCharsets.UTF_8).equals(EXPECTED_BODY)) {
                latencies.add(totalTime / 2);
            }
        }
        return latencies;
    }

}
