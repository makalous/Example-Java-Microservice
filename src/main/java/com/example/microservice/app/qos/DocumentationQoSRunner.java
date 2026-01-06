package com.example.microservice.app.qos;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Component
@RequiredArgsConstructor
public class DocumentationQoSRunner implements ApplicationRunner {
    private final RequestMappingHandlerMapping handlerMapping;

    @Override
    public void run(ApplicationArguments args) {
        int totalEndpoints = countAllEndpoints();
        int documentedEndpoints = countDocumentedEndpoints();
        DocumentationTracker.getInstance()
                .logDocumentationCoverage(totalEndpoints, documentedEndpoints);
    }

    private int countAllEndpoints() {
        return handlerMapping.getHandlerMethods().keySet().stream()
                .mapToInt(handlerMethod -> handlerMethod.getPathPatternsCondition().getPatterns().size())
                .sum();
    }
//TODO
    private int countDocumentedEndpoints() {
        return 2;
    }
}
