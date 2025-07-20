package org.kafka.exception.domain;

import org.kafka.exception.base.BaseDomainException;

public class PaymentDomainException extends BaseDomainException {
    public PaymentDomainException(PaymentDomainErrorCode errorCode) {
        super(errorCode);
    }
}