package org.kafka.security.exceptions;

// ✅ JwtExceptionHandler:
// Authentication veya authorization işlemlerinde oluşan istisnaları merkezi olarak yakalar.
// Özelleştirilmiş hata mesajları ve HTTP status kodları ile dönüş sağlar.
// Her mikroserviste aktif edilmelidir. Bu sayede JWT kaynaklı hatalar düzgün yönetilir.
import org.kafka.exception.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class JwtExceptionHandler {

    // Header yok, token eksik veya yanlışsa
    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<ErrorResponse> handle401(AuthenticationCredentialsNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse("UNAUTHORIZED", ex.getMessage()));
    }

    // Role yetmiyorsa (örn: @PreAuthorize("hasRole('ADMIN')"))
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handle403(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse("FORBIDDEN", ex.getMessage()));
    }

    // Token formatı geçersizse, süresi dolmuşsa vs.
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorResponse> handleJwt(JwtException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse("JWT_INVALID", ex.getMessage()));
    }
}