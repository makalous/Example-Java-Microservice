package com.example.microservice.app.qos;

import java.util.concurrent.atomic.AtomicLong;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReliabilityTracker {
    private static final ReliabilityTracker INSTANCE = new ReliabilityTracker();
    private final AtomicLong totalRequests = new AtomicLong(0);
    private final AtomicLong successfulRequests = new AtomicLong(0);
    private final AtomicLong failedRequests = new AtomicLong(0);
    private ReliabilityTracker() {
    }
    public static ReliabilityTracker getInstance() {
        return INSTANCE;
    }
    public void recordRequest(boolean success) {
        totalRequests.incrementAndGet();
        if (success) {
            successfulRequests.incrementAndGet();
        } else {
            failedRequests.incrementAndGet();
        }
        logCurrentReliability();
    }
    public double getReliability() {
        long total = totalRequests.get();
        if (total == 0) {
            return 1.0;
        }
        return (double) successfulRequests.get() / total;
    }

    private void logCurrentReliability() {
        log.info(
                "Reliability stats: total={}, success={}, failed={}, reliability={}",
                totalRequests.get(),
                successfulRequests.get(),
                failedRequests.get(),
                getReliability()
        );
    }
}
