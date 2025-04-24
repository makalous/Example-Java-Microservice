package com.example.microservice.app.exampleSdk.service;

import lombok.AllArgsConstructor;
import org.example.sdk.ExampleClient;
import org.example.sdk.model.ExampleRspDto;

@AllArgsConstructor
public class ExampleSdkService implements ExampleSdkInterface {
    private final ExampleClient exampleClient;

    @Override
    public ExampleRspDto getExampleResource() {
        return exampleClient.getExample();
    }
}
