package org.example.application.out;

import org.example.domain.Server;

import java.util.List;

public interface ServerService {

    List<Server> servers(int threadsPerUrl);

}
