package com.example.microservice.app.qos;

import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.Instant;

@Slf4j
public class AvailabilityTracker {
    private static final AvailabilityTracker INSTANCE = new AvailabilityTracker();
    private final Instant appStartTime;
    private Duration accumulatedUpTime;
    private Instant lastCheckTime;
    private boolean lastCheckWasUp;
    private AvailabilityTracker() {
        this.appStartTime = Instant.now();
        this.accumulatedUpTime = Duration.ZERO;
        this.lastCheckTime = appStartTime;
        this.lastCheckWasUp = true;
    }
    public static AvailabilityTracker getInstance() {
        return INSTANCE;
    }
    public synchronized void recordHealthCheck(boolean isUp) {
        Instant now = Instant.now();
        if (lastCheckWasUp) {
            Duration interval = Duration.between(lastCheckTime, now);
            accumulatedUpTime = accumulatedUpTime.plus(interval);
        }
        Duration totalUptime = Duration.between(appStartTime, now);
        double availability = totalUptime.isZero() ? 1.0 :
                (double) accumulatedUpTime.toMillis() / totalUptime.toMillis();

        log.info("Health check result: {}, current availability: {}", isUp ? "UP" : "DOWN", availability);
        lastCheckTime = now;
        lastCheckWasUp = isUp;
    }
}