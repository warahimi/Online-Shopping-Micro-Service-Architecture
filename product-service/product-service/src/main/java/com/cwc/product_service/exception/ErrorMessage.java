package com.cwc.product_service.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class ErrorMessage {
    private String timestamp;
    private String error;
    private String message;
    private int status;
    private String path;
    private String url;
}
