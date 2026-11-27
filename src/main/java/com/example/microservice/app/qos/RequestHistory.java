package com.example.microservice.app.qos;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.LockSupport;

@Slf4j
public class RequestHistory {

    private static final List<String> REQUESTS = new ArrayList<>();

    private static final AtomicLong TOTAL_REQUESTS = new AtomicLong();

    public void recordRequest(String requestPath) {

        long requestNumber = TOTAL_REQUESTS.incrementAndGet();

        REQUESTS.add(requestPath + "-" + requestNumber);

        Iterator<String> iterator = REQUESTS.iterator();

        while (iterator.hasNext()) {

            String current = iterator.next();

            LockSupport.parkNanos(500_000);

            if (current.equals(requestPath)) {
                log.debug("Request history match: {}", current);
            }
        }
    }
}