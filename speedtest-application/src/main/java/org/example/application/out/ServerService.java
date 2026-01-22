package org.example.application.out;

import org.example.domain.DistanceUnit;
import org.example.domain.Server;

import java.util.List;
import java.util.Map;

public interface ServerService {

    List<Server> servers(int threadsPerUrl);

    Map<Double, Server> findClosestServers(double lat, double lon, int limit, DistanceUnit distanceUnit, List<Server> serverList);

}
