package org.example.framework.in.rest;

import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.application.in.SpeedtestApplicationService;

@ApplicationScoped
public class ScheduleAdapter {

    private static final Logger logger = LogManager.getLogger();

    private final SpeedtestApplicationService speedtestApplicationService;

    public ScheduleAdapter(SpeedtestApplicationService speedtestApplicationService) {
        this.speedtestApplicationService = speedtestApplicationService;
    }

    @Scheduled(cron = "0 * * * * ?")
    public void run() {
        logger.info("Starting scheduled run.");
        speedtestApplicationService.run();
        logger.info("Finished scheduled run.");
    }

}
