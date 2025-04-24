package com.example.microservice.app.global.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "global.property")
@Component
public class GlobalProperties {
    private String baseUrlFirst;
    private String baseUrlSecond;
    private String authSecretKey;
}
