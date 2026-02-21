package org.example.application.in;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.application.out.ImageStore;
import org.example.application.out.Logger;
import org.example.application.out.Repository;
import org.example.domain.SpeedtestResult;
import org.example.domain.SpeedtestResultID;

import java.io.File;

@ApplicationScoped
public class PersistenceApplicationService {

    private final Logger logger;
    private final ImageStore imageStore;
    private final Repository<SpeedtestResultID, SpeedtestResult> repository;

    public PersistenceApplicationService(
            Logger logger,
            ImageStore imageStore,
            Repository<SpeedtestResultID, SpeedtestResult> repository) {
        this.logger = logger;
        this.imageStore = imageStore;
        this.repository = repository;
    }

    public void store(SpeedtestResult speedtestResult) {
        logger.info("saving share ...");
        File file = imageStore.store(speedtestResult.shareUrl());
        logger.info(file);

        logger.info("saving csv ...");
        repository.create(speedtestResult);
    }

}
