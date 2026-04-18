package com.example.microservice.app.logic.service;

import lombok.AllArgsConstructor;
import org.example.sdk.ExampleClient;
import org.example.sdk.model.ExampleRspDto;

@AllArgsConstructor
public class SimpleServiceSdkImplementation implements SimpleServiceInterface {
    private final ExampleClient exampleClient;
    @Override
    public ExampleRspDto getExampleResource() {
        return exampleClient.getExample();
    }
}
