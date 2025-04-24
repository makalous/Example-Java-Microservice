package com.example.microservice.app.exampleSdk.controller;

import com.example.microservice.app.exampleSdk.service.ExampleSdkInterface;
import com.example.microservice.app.global.error.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.example.sdk.model.ExampleRspDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/private")
@Tag(name = "Example SDK", description = "Endpoints for testing the Example SDK")
public class ExampleSdkController {
    private ExampleSdkInterface exampleSdk;

    @Operation(
            summary = "Get example resource",
            description = "Returns data fetched via Example SDK from external API"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful response",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ExampleRspDto.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Unexpected error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @GetMapping(value = "/getExample", produces = "application/json")
    public ResponseEntity<ExampleRspDto> getExample() {
        return ResponseEntity.ok(exampleSdk.getExampleResource());
    }
}
