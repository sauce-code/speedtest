package org.example.framework.out.transfer;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.application.out.Logger;
import org.example.domain.TransferTestResult;
import org.example.util.Objectz;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.*;

@ApplicationScoped
public class TransferService {

    private final Logger logger;

    public TransferService(Logger logger) {
        this.logger = logger;
    }

    public TransferTestResult testTransfer(List<? extends Callable<TransferTestResult>> callables, int threads) {
        Objects.requireNonNull(callables);
        Objectz.require(threads > 0);
        try (ExecutorService executorService = Executors.newWorkStealingPool(threads)) {
            List<TransferTestResult> results = executorService.invokeAll(callables)
                    .stream()
                    .map(this::get)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList();
            int bytes = results.stream()
                    .map(TransferTestResult::bytes)
                    .mapToInt(Integer::intValue)
                    .sum();
            long durationInMs = results.stream()
                    .map(TransferTestResult::durationInMs)
                    .mapToLong(Long::longValue)
                    .sum() / threads;
            return new TransferTestResult(bytes, durationInMs);
        } catch (InterruptedException e) {
            logger.warn("Interrupted.");
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    private Optional<TransferTestResult> get(Future<TransferTestResult> future) {
        try {
            return Optional.of(future.get());
        } catch (InterruptedException e) {
            logger.warn("Interrupted.");
            Thread.currentThread().interrupt();
            return Optional.empty();
        } catch (ExecutionException e) {
            logger.warn(e.getMessage());
            return Optional.empty();
        }
    }

}
