package org.example.application.in;

import org.example.application.out.LockService;
import org.example.application.out.Logger;
import org.example.domain.SpeedtestResultFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class SpeedtestApplicationServiceTest {

    SpeedtestApplicationService speedtestApplicationService;
    Logger logger;
    LockService lockService;
    RunApplicationService runApplicationService;
    PersistenceApplicationService persistenceApplicationService;

    @BeforeEach
    void setUp() {
        logger = mock(Logger.class);
        lockService = mock(LockService.class);
        runApplicationService = mock(RunApplicationService.class);
        persistenceApplicationService = mock(PersistenceApplicationService.class);
        speedtestApplicationService = new SpeedtestApplicationService(
                logger,
                lockService,
                runApplicationService,
                persistenceApplicationService);
    }

    @Test
    void givenSome_whenLockSet_thenThrow() {
        when(lockService.setBusy())
                .thenReturn(false);
        assertThrows(ApplicationLockException.class, () -> speedtestApplicationService.run());
        verify(lockService, never()).reset();
    }

    @Test
    void givenSome_whenRun_thenThrow() {
        when(lockService.setBusy())
                .thenReturn(true);
        when(runApplicationService.run())
                .thenThrow(RuntimeException.class);
        assertThrows(ApplicationRunException.class, () -> speedtestApplicationService.run());
        verify(lockService).reset();
    }

    @Test
    void givenSome_whenPersistenceException_thenThrow() {
        when(lockService.setBusy())
                .thenReturn(true);
        when(runApplicationService.run())
                .thenReturn(SpeedtestResultFixture.some());
        doThrow(new RuntimeException())
                .when(persistenceApplicationService)
                .store(SpeedtestResultFixture.some());
        assertThrows(ApplicationRunException.class, () -> speedtestApplicationService.run());
        verify(lockService).reset();
    }

    @Test
    void givenSome_whenNoErrors_thenNotThrow() {
        when(lockService.setBusy())
                .thenReturn(true);
        when(runApplicationService.run())
                .thenReturn(SpeedtestResultFixture.some());
        assertDoesNotThrow(() -> speedtestApplicationService.run());
        verify(lockService).reset();
    }

}
