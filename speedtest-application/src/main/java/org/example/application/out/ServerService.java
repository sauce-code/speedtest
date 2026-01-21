package org.example.application.out;

import org.example.application.out.model.DistanceUnit;
import org.example.domain.DomainServer;

import java.util.List;
import java.util.Map;

public interface ServerService {

    List<DomainServer> servers(int threadsPerUrl);

    Map<Double, DomainServer> findClosestServers(double lat, double lon, int limit, DistanceUnit distanceUnit, List<DomainServer> domainServerList);

}
