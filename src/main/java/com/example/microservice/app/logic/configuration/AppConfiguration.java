package com.example.microservice.app.logic.configuration;

import com.example.microservice.app.logic.service.SimpleServiceInterface;
import com.example.microservice.app.logic.service.SimpleServiceImplementation;
import com.example.microservice.app.logic.service.SimpleServiceSdkImplementation;
import com.example.microservice.app.global.property.GlobalProperties;
import lombok.AllArgsConstructor;
import org.example.sdk.ExampleClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Configuration
@Component
@AllArgsConstructor
public class AppConfiguration {
    private final GlobalProperties globalProperties;

    @Bean
    public SimpleServiceInterface simpleSdk(final ExampleClient exampleClient) {
        return new SimpleServiceSdkImplementation(exampleClient);
    }

    @Bean
    @Primary
    public SimpleServiceInterface simpleCall(RestTemplate restTemplate) {
        return new SimpleServiceImplementation(restTemplate);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @Profile("host99")
    public ExampleClient exampleClient99() {
        return new ExampleClient(globalProperties.getBaseUrlFirst());
    }

    @Bean
    @Profile("host100")
    public ExampleClient exampleClient100() {
        return new ExampleClient(globalProperties.getBaseUrlSecond());
    }
}
