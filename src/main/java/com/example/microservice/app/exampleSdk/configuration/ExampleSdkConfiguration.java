package com.example.microservice.app.exampleSdk.configuration;

import com.example.microservice.app.exampleSdk.service.ExampleSdkInterface;
import com.example.microservice.app.exampleSdk.service.ExampleSdkService2;
import com.example.microservice.app.global.property.GlobalProperties;
import lombok.AllArgsConstructor;
import org.example.sdk.ExampleClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Configuration
@Component
@AllArgsConstructor
public class ExampleSdkConfiguration {
    private final GlobalProperties globalProperties;

//    @Bean
//    public ExampleSdkInterface exampleSdk(final ExampleClient exampleClient) {
//        return new ExampleSdkService(exampleClient);
//    }
    @Bean
    public ExampleSdkInterface exampleSdk(RestTemplate restTemplate) {
        return new ExampleSdkService2(restTemplate);
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
