package org.kafka.exception.handler;

import org.kafka.exception.dto.ErrorResponse;
import org.kafka.exception.base.BaseDomainException;
import org.kafka.exception.base.BaseErrorCode;
import org.kafka.exception.base.BaseValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseDomainException.class)
    public ResponseEntity<ErrorResponse> handleDomainException(BaseDomainException ex) {
        BaseErrorCode errorCode = ex.getErrorCode();
        return new ResponseEntity<>(
                new ErrorResponse(errorCode.getCode(), errorCode.getMessage()),
                errorCode.getStatus());
    }

    // @NotNull @NotBlank @Max vs. yüzeysel validasyonlar için
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

    // Mantıksal Validasyonlar için
    // "Bu ödeme metodu pasif”, “Aynı kullanıcı aynı gün 2 kez ödeme yapamaz" gibi
    @ExceptionHandler(BaseValidationException.class)
    public ResponseEntity<ErrorResponse> handleBusinessRuleValidation(BaseValidationException ex) {
        BaseErrorCode errorCode = ex.getErrorCode();
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
