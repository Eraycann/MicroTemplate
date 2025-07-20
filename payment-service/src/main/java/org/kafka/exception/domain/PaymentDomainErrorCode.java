package org.kafka.exception.domain;


import org.kafka.exception.base.BaseErrorCode;
import org.springframework.http.HttpStatus;

public enum PaymentDomainErrorCode implements BaseErrorCode {
    PAYMENT_NOT_FOUND("PAYMENT-404", "Payment not found", HttpStatus.NOT_FOUND),
    PAYMENT_ALREADY_DELETED("PAYMENT-410", "Payment already deleted", HttpStatus.GONE),
    PAYMENT_UPDATE_CONFLICT("PAYMENT-409", "Payment update conflict detected", HttpStatus.CONFLICT);

    private final String code;
    private final String message;
    private final HttpStatus status;

    PaymentDomainErrorCode(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    public String getCode() { return code; }
    public String getMessage() { return message; }
    public HttpStatus getStatus() { return status; }
}