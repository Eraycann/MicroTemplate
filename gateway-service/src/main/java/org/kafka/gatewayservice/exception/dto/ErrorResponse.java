package org.kafka.gatewayservice.exception.dto;

// 📦 API tarafından döndürülecek hata nesnesi
public class ErrorResponse {
    private String code;    // Örneğin: GATEWAY_ERROR, SERVICE_UNAVAILABLE
    private String message; // Hata mesajı

    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return code; }
    public String getMessage() { return message; }
}
