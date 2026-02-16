package org.example.framework.in.schedule;

import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import org.example.application.in.SpeedtestApplicationService;
import org.example.application.out.Logger;

@ApplicationScoped
public class ScheduleAdapter {

    private final Logger logger;
    private final SpeedtestApplicationService speedtestApplicationService;

    public ScheduleAdapter(
            Logger logger,
            SpeedtestApplicationService speedtestApplicationService) {
        this.logger = logger;
        this.speedtestApplicationService = speedtestApplicationService;
    }

    @Scheduled(cron = "0 * * * * ?")
    public void run() {
        logger.info("Starting scheduled run.");
        speedtestApplicationService.run();
        logger.info("Finished scheduled run.");
    }

}
