package org.example.framework.in.rest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.example.application.in.SpeedtestApplicationService;
import org.example.domain.SpeedtestResult;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.GregorianCalendar;

@Path("/")
@ApplicationScoped
public class RestAdapter {

    private final SpeedtestApplicationService speedtestApplicationService;

    @Inject
    public RestAdapter(SpeedtestApplicationService speedtestApplicationService) {
        this.speedtestApplicationService = speedtestApplicationService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("")
    public SpeedtestResult run() {
        return speedtestApplicationService.run();
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
