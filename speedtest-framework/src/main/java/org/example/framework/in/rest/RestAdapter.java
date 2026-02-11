package org.example.framework.in.rest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.example.application.in.SpeedtestApplicationService;
import org.example.domain.SpeedtestResult;

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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/greet")
    public String runPost() {

//        GregorianCalendar gregorianCalendar = GregorianCalendar.from(ZonedDateTime.now());
//        gregorianCalendar.get
//        now.get
//        System.out.println(now.toEpochSecond(LocalTime.now(), ZoneOffset.UTC));

        System.out.println(System.currentTimeMillis());


        return "Hello";
    }

}
