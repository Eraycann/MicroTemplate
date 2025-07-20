package org.kafka.userservice.exception.domain;

import lombok.Getter;

@Getter
public class UserDomainException extends RuntimeException {
    private final UserDomainErrorCode errorCode;

    public UserDomainException(UserDomainErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
