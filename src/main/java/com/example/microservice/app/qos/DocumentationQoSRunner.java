package com.example.microservice.app.qos;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
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
    private int countDocumentedEndpoints() {
        int documented = 0;
        for (HandlerMethod method: handlerMapping.getHandlerMethods().values()) {
            if (isDocumented(method)) {
                documented++;
            }
        }
        return documented;
    }
    private boolean isDocumented(HandlerMethod method) {
        if (method.hasMethodAnnotation(Hidden.class)) return false;
        Operation op = method.getMethodAnnotation(Operation.class);
        if (op == null || op.hidden()) return false;
        return !method.getBeanType().isAnnotationPresent(Hidden.class);
    }
}