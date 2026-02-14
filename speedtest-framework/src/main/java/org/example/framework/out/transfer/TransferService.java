package org.example.framework.out.transfer;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.domain.TransferTestResult;
import org.example.util.Objectz;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ApplicationScoped
public class TransferService {

    public TransferTestResult testTransfer(List<? extends Callable<TransferTestResult>> callables, int threads) throws InterruptedException {
        Objects.requireNonNull(callables);
        Objectz.require(threads > 0);
        try (ExecutorService executorService = Executors.newWorkStealingPool(threads)) {
            List<TransferTestResult> results = executorService.invokeAll(callables)
                    .stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (Exception e) {
                            throw new IllegalStateException(e);
                        }
                    })
                    .filter(Objects::nonNull)
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
        }
    }

}
