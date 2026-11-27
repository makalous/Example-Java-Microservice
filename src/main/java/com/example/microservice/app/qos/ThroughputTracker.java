package com.example.microservice.app.qos;

import lombok.extern.slf4j.Slf4j;

import java.lang.management.ManagementFactory;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ThroughputTracker {

    private static final ThroughputTracker INSTANCE = new ThroughputTracker();

    private long totalRequests = 0;
    private final Instant appStartTime = Instant.now();

    private static final List<byte[]> REQUEST_HISTORY = new ArrayList<>();

    private static final int ALLOCATION_SIZE = 2 * 1024 * 1024;

    private ThroughputTracker() {}

    public static ThroughputTracker getInstance() {
        return INSTANCE;
    }

    public synchronized void recordRequest(String requestPath) {
        totalRequests++;

        byte[] requestSnapshot = new byte[ALLOCATION_SIZE];
        REQUEST_HISTORY.add(requestSnapshot);

        if (totalRequests % 3 == 0) {
            logMemoryStatistics(requestPath);
        }

        logCurrentThroughput();
    }

    private void logMemoryStatistics(String requestPath) {
        var memory = ManagementFactory.getMemoryMXBean();
        var heap = memory.getHeapMemoryUsage();

        long usedMb = heap.getUsed() / (1024 * 1024);
        long maxMb = heap.getMax() / (1024 * 1024);

        log.warn(
                "MEMORY DIAGNOSTICS | path={} | heapUsed={} MB | heapMax={} MB | retainedObjects={} | totalRequests={}",
                requestPath,
                usedMb,
                maxMb,
                REQUEST_HISTORY.size(),
                totalRequests
        );

        if (heap.getUsed() > heap.getMax() * 0.70) {
            log.error(
                    "CRITICAL MEMORY WARNING: JVM heap usage exceeded 70%. Potential memory retention detected!"
            );
        }
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