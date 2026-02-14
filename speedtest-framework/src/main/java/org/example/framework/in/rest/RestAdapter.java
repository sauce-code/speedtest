package org.example.framework.in.rest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.example.application.in.SpeedtestApplicationService;

@Path("/")
@ApplicationScoped
public class RestAdapter {

    private final SpeedtestApplicationService speedtestApplicationService;

    public RestAdapter(SpeedtestApplicationService speedtestApplicationService) {
        this.speedtestApplicationService = speedtestApplicationService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("")
    public SpeedtestResultView run() {
        var speedtestResult = speedtestApplicationService.run();
        return SpeedtestResultView.from(speedtestResult);
    }

}
