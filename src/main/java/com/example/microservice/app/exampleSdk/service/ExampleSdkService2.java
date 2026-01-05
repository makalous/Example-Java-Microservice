package com.example.microservice.app.exampleSdk.service;

import com.example.microservice.app.global.dto.CoffeRspDto;
import lombok.AllArgsConstructor;
import org.example.sdk.model.ExampleRspDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@AllArgsConstructor
public class ExampleSdkService2 implements ExampleSdkInterface {
    private static final String URL = "https://api.sampleapis.com/coffee/hot";
    private final RestTemplate restTemplate;
    @Override
    public ExampleRspDto getExampleResource() {
        ResponseEntity<List<CoffeRspDto>> rsp =
                restTemplate.exchange(
                        URL,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {
                        }
                );
        var toReturn = new ExampleRspDto();
        toReturn.setExampleOne(rsp.getBody().get(0).getDescription());
        toReturn.setExampleTwo(rsp.getBody().get(0).getId());
        toReturn.setExampleThree(rsp.getBody().get(0).getTitle());
        return toReturn;
    }
}