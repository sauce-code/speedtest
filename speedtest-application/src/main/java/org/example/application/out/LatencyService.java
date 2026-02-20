package org.example.application.out;

import org.example.domain.ServerDistanceResult;
import org.example.domain.ServerLatencyResult;

import java.util.List;

public interface LatencyService {

    ServerLatencyResult getFastestServer(List<ServerDistanceResult> serverDistanceResults);

}
