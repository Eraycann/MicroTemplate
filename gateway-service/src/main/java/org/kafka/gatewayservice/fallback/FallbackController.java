package org.kafka.gatewayservice.fallback;

import org.kafka.gatewayservice.exception.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    // 🔁 User servisi hatalıysa dönecek fallback mesajı
    @GetMapping("/user")
    public ResponseEntity<ErrorResponse> userFallback() {
        ErrorResponse errorResponse = new ErrorResponse(
                "SERVICE_UNAVAILABLE",
                "User Service geçici olarak devre dışı. Lütfen daha sonra tekrar deneyin."
        );
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(errorResponse);
    }

    // 🔁 Payment servisi hatalıysa dönecek fallback mesajı
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
