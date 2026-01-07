package com.example.microservice.app.qos;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IntegrityTracker {
    private static final IntegrityTracker INSTANCE = new IntegrityTracker();
    private long totalTransmissions = 0;
    private long correctTransmissions = 0;

    private IntegrityTracker() {}

    public static IntegrityTracker getInstance() {
        return INSTANCE;
    }

    public synchronized void recordTransmission(boolean correct) {
        totalTransmissions++;
        if (correct) {
            correctTransmissions++;
        }
        logCurrentIntegrity();
    }

    public < T > boolean checkIntegrity(T rsp) {
        try {
            String json = new ObjectMapper().writeValueAsString(rsp);
            return !json.contains("null");
        } catch (Exception e) {
            return false;
        }
    }

    private synchronized long getTotalTransmissions() {
        return totalTransmissions;
    }

    private synchronized long getCorrectTransmissions() {
        return correctTransmissions;
    }

    public synchronized double getIntegrity() {
        if (totalTransmissions == 0) return 1.0;
        return (double) getCorrectTransmissions() / getTotalTransmissions();
    }

    private void logCurrentIntegrity() {
        log.info(
                "Data integrity stats: {}",
                getIntegrity()
        );
    }
}