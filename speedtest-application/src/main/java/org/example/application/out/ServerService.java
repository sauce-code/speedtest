package org.example.application.out;

import org.example.domain.Distance;
import org.example.domain.Location;
import org.example.domain.Server;

import java.util.List;
import java.util.Map;

public interface ServerService {

    List<Server> servers(int threadsPerUrl);

    Map<Distance, Server> findClosestServers(Location clientLocation, int limit, List<Server> serverList);

}
