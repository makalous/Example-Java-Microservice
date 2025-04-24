package com.example.microservice.app.global.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ErrorResponse {
    @Schema(example = "Unexpected error")
    private String error;

    @Schema(example = "NullPointerException at line 42")
    private String message;
}
