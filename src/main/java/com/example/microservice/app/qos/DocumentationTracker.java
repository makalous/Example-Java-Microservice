package com.example.microservice.app.qos;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class DocumentationTracker {
    private static final DocumentationTracker INSTANCE = new DocumentationTracker();
    private DocumentationTracker() {}

    public static DocumentationTracker getInstance() {
        return INSTANCE;
    }
    public void logDocumentationCoverage(int totalEndpoints, int documentedEndpoints) {
        double coverage = totalEndpoints == 0 ?
                1.0 :
                (double) documentedEndpoints / totalEndpoints;

        log.info(
                "Documentation QoS: documented={} total={} coverage={}%",
                documentedEndpoints,
                totalEndpoints,
                coverage * 100
        );
    }
}