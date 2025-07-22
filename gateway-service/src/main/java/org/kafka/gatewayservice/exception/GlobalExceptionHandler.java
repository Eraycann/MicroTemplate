package org.kafka.gatewayservice.exception;

import org.kafka.gatewayservice.exception.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 💥 Tüm exception'ları yakalayıp ErrorResponse DTO'su ile döner
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "GATEWAY_ERROR",
                ex.getMessage() != null ? ex.getMessage() : "Bilinmeyen bir hata oluştu."
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }
}
