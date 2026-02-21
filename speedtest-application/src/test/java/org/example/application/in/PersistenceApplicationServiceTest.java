package org.example.application.in;

import org.example.application.out.ImageStore;
import org.example.application.out.Logger;
import org.example.application.out.Repository;
import org.example.domain.SpeedtestResult;
import org.example.domain.SpeedtestResultFixture;
import org.example.domain.SpeedtestResultID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class PersistenceApplicationServiceTest {

    Logger logger;
    ImageStore imageStore;
    Repository<SpeedtestResultID, SpeedtestResult> repository;
    PersistenceApplicationService applicationService;

    @BeforeEach
    @SuppressWarnings("unchecked")
    void setUp() {
        logger = mock(Logger.class);
        imageStore = mock(ImageStore.class);
        repository = mock(Repository.class);
        applicationService = new PersistenceApplicationService(logger, imageStore, repository);
    }

    @Test
    void verifyStore() {
        applicationService.store(SpeedtestResultFixture.some());
        verify(imageStore)
                .store(SpeedtestResultFixture.some().shareUrl());
        verify(repository)
                .create(SpeedtestResultFixture.some());
    }

}