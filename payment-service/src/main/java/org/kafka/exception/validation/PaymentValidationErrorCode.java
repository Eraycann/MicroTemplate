package org.kafka.exception.validation;

import org.kafka.exception.base.BaseErrorCode;
import org.springframework.http.HttpStatus;

public enum PaymentValidationErrorCode implements BaseErrorCode {
    PAYMENT_METHOD_NOT_FOUND("PAYMENT-VALID-001", "Payment method not found", HttpStatus.BAD_REQUEST),
    PAYMENT_METHOD_IS_INACTIVE("PAYMENT-VALID-002", "Payment method is inactive", HttpStatus.BAD_REQUEST),
    PAYMENT_DATE_CANNOT_BE_IN_FUTURE("PAYMENT-VALID-003", "Payment date cannot be in the future", HttpStatus.BAD_REQUEST),
    SAME_DAY_DUPLICATE_PAYMENT("PAYMENT-VALID-004", "Duplicate payment on same day", HttpStatus.CONFLICT);



    private final String code;
    private final String message;
    private final HttpStatus status;

    PaymentValidationErrorCode(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    public String getCode() { return code; }
    public String getMessage() { return message; }
    public HttpStatus getStatus() { return status; }
}