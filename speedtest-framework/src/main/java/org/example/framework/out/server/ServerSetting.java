package org.example.framework.out.server;

import org.example.domain.Server;

import java.util.List;

public record ServerSetting(
        List<Server> servers
) {

}
