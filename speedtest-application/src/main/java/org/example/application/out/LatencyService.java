package org.example.application.out;

import org.example.domain.LatencyTestResult;
import org.example.domain.Server;

import java.util.Map;

public interface LatencyService {

    Map.Entry<Server, LatencyTestResult> getFastestServer(final Map<Double, Server> serverMap);

}
