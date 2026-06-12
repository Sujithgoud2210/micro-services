package com.springworks.api_gateway_service.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class ErrorResponseDto {
    private boolean success;

    private LocalDateTime timestamp;

    private int status;

    private String error;

    private String message;

    private String path;
}
