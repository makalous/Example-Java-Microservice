package com.example.microservice.app.qos;

import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.Instant;
@Slf4j
public class ThroughputTracker {
    private static final ThroughputTracker INSTANCE = new ThroughputTracker();
    private long totalRequests = 0;
    private final Instant appStartTime = Instant.now();
    private ThroughputTracker() {
    }
    public static ThroughputTracker getInstance() {
        return INSTANCE;
    }
    public synchronized void recordRequest() {
        totalRequests++;
        logCurrentThroughput();
    }
    public synchronized long getTotalRequests() {
        return totalRequests;
    }

    public Duration getUptime() {
        return Duration.between(appStartTime, Instant.now());
    }

    public double getThroughputPerSecond() {
        Duration uptime = getUptime();
        double seconds = uptime.toMillis() / 1000.0;
        return seconds > 0 ? totalRequests / seconds : 0;
    }
    private void logCurrentThroughput() {
        log.info(
                "Throughput stats: {} invocations/s",
                getThroughputPerSecond()
        );
    }
}