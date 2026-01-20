package org.example.application.out;

import org.example.application.out.model.DistanceUnit;
import org.example.domain.LatencyTestResult;
import org.example.domain.Server;

import java.util.List;
import java.util.Map;

public interface ServerService {

    List<Server> servers();

    Map<Double, Server> findClosestServers(double lat, double lon, int limit, DistanceUnit distanceUnit, List<Server> serverList);

    Map.Entry<Server, LatencyTestResult> getFastestServer(Map<Double, Server> closestServers);
}
