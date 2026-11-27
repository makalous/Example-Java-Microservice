package com.example.microservice.app.logic.service;

import java.util.ArrayList;
import java.util.List;

public class RequestHistory {

    private final List<String> history = new ArrayList<>();

    public long recordAndCount(String requestId) {
        history.add(requestId);

        return history.stream()
                .filter(requestId::equals)
                .count();
    }
}