package org.example.application.out;

import org.example.domain.Server;
import org.example.domain.location.Distance;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public interface ServerService {

    List<Server> servers(int threadsPerUrl);

    Map<Distance, Server> limit(TreeMap<Distance, Server> treeMap);

}
