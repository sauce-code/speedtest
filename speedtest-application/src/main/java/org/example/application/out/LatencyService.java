package org.example.application.out;

import org.example.domain.DomainServer;
import org.example.domain.LatencyTestResult;

import java.util.Map;

public interface LatencyService {

    Map.Entry<DomainServer, LatencyTestResult> getFastestServer(final Map<Double, DomainServer> serverMap);

}
