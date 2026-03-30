package org.example.application.in;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.application.out.LockService;
import org.example.application.out.Logger;
import org.example.domain.SpeedtestResult;

@ApplicationScoped
public class SpeedtestApplicationService {

    private final Logger logger;
    private final LockService lockService;
    private final RunApplicationService runApplicationService;
    private final PersistenceApplicationService persistenceApplicationService;

    public SpeedtestApplicationService(
            Logger logger,
            LockService lockService,
            RunApplicationService runApplicationService,
            PersistenceApplicationService persistenceApplicationService) {
        this.logger = logger;
        this.lockService = lockService;
        this.runApplicationService = runApplicationService;
        this.persistenceApplicationService = persistenceApplicationService;
    }

    public SpeedtestResult run() {
        logger.info("setting lock ...");
        if (!lockService.setBusy()) {
            var message = "Application is already busy.";
            logger.error(message);
            throw new ApplicationLockException(message);
        }
        try {
            var speedtestResult = runApplicationService.run();
            persistenceApplicationService.store(speedtestResult);
            return speedtestResult;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ApplicationRunException(e);
        } finally {
            logger.info("resetting lock ...");
            lockService.reset();
        }
    }

}
