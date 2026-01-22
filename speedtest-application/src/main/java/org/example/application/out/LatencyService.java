package org.example.application.out;

import org.example.domain.Server;
import org.example.domain.LatencyTestResult;

import java.util.Map;

public interface LatencyService {

    Map.Entry<Server, LatencyTestResult> getFastestServer(final Map<Double, Server> serverMap);

}
