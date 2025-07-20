package org.kafka.gatewayservice.controller;

import org.kafka.gatewayservice.exception.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/user")
    public ResponseEntity<ErrorResponse> userFallback() {
        ErrorResponse errorResponse = new ErrorResponse(
                "SERVICE_UNAVAILABLE",
                "User Service geçici olarak devre dışı. Lütfen daha sonra tekrar deneyin."
        );
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(errorResponse);
    }

    @GetMapping("/payment")
    public ResponseEntity<ErrorResponse> paymentFallback() {
        ErrorResponse errorResponse = new ErrorResponse(
                "SERVICE_UNAVAILABLE",
                "Payment Service geçici olarak devre dışı. Lütfen daha sonra tekrar deneyin."
        );
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(errorResponse);
    }
}
