package org.example.framework.out.transfer;

import org.example.domain.TransferTestResult;
import org.example.framework.out.Util;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class TransferService {

    public TransferTestResult testTransfer(List<Callable<TransferTestResult>> callables, int threads) throws InterruptedException {
        if (callables != null && !callables.isEmpty() && threads > 0) {
            List<TransferTestResult> results = Executors.newWorkStealingPool(threads).invokeAll(callables)
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
            if (results.isEmpty()) {
                throw new MissingResultException("Empty list for transfer results");
            }
            int bytes = results.stream()
                    .map(TransferTestResult::bytes)
                    .mapToInt(Integer::intValue)
                    .sum();
            long durationInMs = results.stream()
                    .map(TransferTestResult::durationInMs)
                    .mapToLong(Long::longValue)
                    .sum() / threads;
            return new TransferTestResult(Util.calculateMbps(bytes, durationInMs), bytes, durationInMs);
        } else {
            throw new IllegalArgumentException();
        }
    }

}
