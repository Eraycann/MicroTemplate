package org.kafka.userservice.exception.dto;

import java.util.List;

/**
 * GlobalExceptionHandler içinde hataların döneceği DTO yapısı.
 */
public class ErrorResponse {
    private String code;
    private String message;
    private List<String> errors;

    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ErrorResponse(String code, List<String> errors) {
        this.code = code;
        this.errors = errors;
    }

    public String getCode() { return code; }
    public String getMessage() { return message; }
    public List<String> getErrors() { return errors; }
}
