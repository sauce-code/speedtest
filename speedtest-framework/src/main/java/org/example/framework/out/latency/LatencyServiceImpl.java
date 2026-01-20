package org.example.framework.out.latency;

import org.example.application.out.LatencyService;
import org.example.domain.LatencyTestResult;
import org.example.domain.Server;
import org.example.framework.out.Util;
import org.example.framework.out.http.HttpGetClient;
import org.example.framework.out.http.ServerRequestException;

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
    public Map.Entry<Server, LatencyTestResult> getFastestServer(Map<Double, Server> serverMap) {
        if (serverMap != null && !serverMap.isEmpty()) {
            return findServerLatencies(serverMap).entrySet().stream()
                    .min(Comparator.comparing(entry -> entry.getValue().latency()))
                    .orElseThrow(MissingResultException::new);
        } else {
            throw new IllegalArgumentException();
        }
    }

    private Map<Server, LatencyTestResult> findServerLatencies(final Map<Double, Server> serverMap) throws MissingResultException {
        if (serverMap != null && !serverMap.isEmpty()) {
            final int testsPerServer = Integer.parseInt(Objects.requireNonNull(Util.getConfigProperty("Latency.testsPerServer.maxNumber")));
            final Map<Server, LatencyTestResult> results = new HashMap<>();
            for (final Map.Entry<Double, Server> entry : serverMap.entrySet()) {
                if (entry != null) {
                    try {
                        results.put(entry.getValue(), new LatencyTestResult(calculateAverage(
                                testLatency(entry.getValue().url(), testsPerServer)), entry.getKey()));
                    } catch (ServerRequestException | MissingResultException e) {

                    }
                }
            }
            if (!results.isEmpty()) {
                return results;
            } else {
                throw new MissingResultException("Empty map for latency tests");
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    private double calculateAverage(final List<Long> list) throws MissingResultException {
        if (list != null && !list.isEmpty()) {
            if (list.stream()
                    .filter(Objects::nonNull)
                    .mapToLong(Long::longValue)
                    .average().isPresent()) {
                return list.stream()
                        .filter(Objects::nonNull)
                        .mapToLong(Long::longValue)
                        .average()
                        .getAsDouble();
            } else {
                throw new MissingResultException("Unable to calculate average");
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    private List<Long> testLatency(final String serverUrl, final int limit) throws ServerRequestException {
        if (serverUrl != null && limit > 0) {
            final List<Long> latencies = new ArrayList<>();
            for (int iter = 0; iter < limit; iter++) {
                final String testUrl = serverUrl + TEST_FILE + System.currentTimeMillis();
                final long startTimestamp = System.currentTimeMillis();
                final byte[] bytes = httpGetClient.get(testUrl);
                final long totalTime = System.currentTimeMillis() - startTimestamp;
                if (bytes != null && new String(bytes, StandardCharsets.UTF_8).equals(EXPECTED_BODY)) {
                    latencies.add(totalTime / 2);
                }
            }
            return latencies;
        } else {
            throw new IllegalArgumentException();
        }
    }

}
