package org.example.application.out;

import org.example.domain.location.Distance;
import org.example.domain.FastestServerResult;
import org.example.domain.Server;

import java.util.Map;

public interface LatencyService {

    FastestServerResult getFastestServer(Map<Distance, Server> serverMap);

}
