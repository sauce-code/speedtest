package org.example.application.out;

import org.example.domain.ServerDistance;

import java.util.List;

public interface LatencyService {

    ServerLatencyResult getFastestServer(List<ServerDistance> serverDistances);

}
