package org.kafka.userservice.exception.handler;

import org.kafka.userservice.exception.domain.UserDomainErrorCode;
import org.kafka.userservice.exception.domain.UserDomainException;
import org.kafka.userservice.exception.dto.ErrorResponse;
import org.kafka.userservice.exception.validation.UserValidationErrorCode;
import org.kafka.userservice.exception.validation.UserValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Uygulama genelinde fırlatılan exception'ları yakalayarak
 * anlamlı ErrorResponse objesiyle cevap döner.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserDomainException.class)
    public ResponseEntity<ErrorResponse> handleDomainException(UserDomainException ex) {
        UserDomainErrorCode errorCode = ex.getErrorCode();
        return new ResponseEntity<>(
                new ErrorResponse(errorCode.getCode(), errorCode.getMessage()),
                errorCode.getStatus());
    }

    // Validation anotasyonları hataları (örn. @NotBlank, @Email vs.)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleFieldValidation(MethodArgumentNotValidException ex) {
        List<String> validationErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        return new ResponseEntity<>(
                new ErrorResponse("REQUEST_VALIDATION_ERROR", validationErrors),
                HttpStatus.BAD_REQUEST
        );
    }

    // İş kuralı validasyonları
    @ExceptionHandler(UserValidationException.class)
    public ResponseEntity<ErrorResponse> handleBusinessRuleValidation(UserValidationException ex) {
        UserValidationErrorCode errorCode = ex.getErrorCode();
        return new ResponseEntity<>(
                new ErrorResponse(errorCode.getCode(), errorCode.getMessage()),
                errorCode.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        return new ResponseEntity<>(
                new ErrorResponse("GENERIC-500", ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
