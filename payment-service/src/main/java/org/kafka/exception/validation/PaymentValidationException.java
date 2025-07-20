package org.kafka.exception.validation;

import org.kafka.exception.base.BaseValidationException;

public class PaymentValidationException extends BaseValidationException {
    public PaymentValidationException(PaymentValidationErrorCode errorCode) {
        super(errorCode);
    }
}