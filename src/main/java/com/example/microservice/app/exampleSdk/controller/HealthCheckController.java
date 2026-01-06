package com.example.microservice.app.exampleSdk.controller;

import com.example.microservice.app.qos.AvailabilityTracker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@AllArgsConstructor
@RequestMapping("/private")
@Slf4j
@Tag(name = "Health", description = "Service health endpoints")
public class HealthCheckController {
    @Operation(
            summary = "Service health check",
            description = "Returns HTTP 200 if the service is UP"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Service is healthy",
                    content = @Content(
                            schema = @Schema(implementation = String.class))
            )
    })
    @GetMapping(value = "/health")
    public ResponseEntity<String> health() {
        if (Instant.now().getEpochSecond() % 2 == 0) {
            boolean isUp = true;
            AvailabilityTracker.getInstance().recordHealthCheck(isUp);
            return ResponseEntity.ok("UP");
        } else {
            boolean isUp = false;
            AvailabilityTracker.getInstance().recordHealthCheck(isUp);
            return ResponseEntity.internalServerError().body("DOWN");
        }
    }

}