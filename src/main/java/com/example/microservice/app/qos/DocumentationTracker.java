package com.example.microservice.app.qos;

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

        System.out.printf(
                "Documentation QoS: documented=%d total=%d coverage=%.2f%%%n",
                documentedEndpoints,
                totalEndpoints,
                coverage * 100
        );
    }
}