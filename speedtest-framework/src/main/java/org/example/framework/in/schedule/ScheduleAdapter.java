package org.example.framework.in.schedule;

import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import org.example.application.in.SpeedtestApplicationService;
import org.example.application.out.Logger;

@ApplicationScoped
public class ScheduleAdapter {

    private final Logger logger;
    private final Properties properties;
    private final SpeedtestApplicationService speedtestApplicationService;

    public ScheduleAdapter(
            Logger logger,
            Properties properties,
            SpeedtestApplicationService speedtestApplicationService) {
        this.logger = logger;
        this.properties = properties;
        this.speedtestApplicationService = speedtestApplicationService;
    }

    @Scheduled(cron = "{speedtest.schedule.cron}")
    public void run() {
        if (!properties.enabled()) {
            logger.debug("Skipped scheduled run.");
            return;
        }
        logger.info("Starting scheduled run.");
        speedtestApplicationService.run();
        logger.info("Finished scheduled run.");
    }

}
