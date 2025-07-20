package org.kafka.gatewayservice.exception.dto;


import java.util.List;

// exceptionla birlikte var.
// hata fırlatıldığı zaman, handlerde yakalar ve geri dönüş DTO'su, ErrorResponse şeklindedir.
public class ErrorResponse {
    private String code;
    private String message;

    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return code; }
    public String getMessage() { return message; }
}