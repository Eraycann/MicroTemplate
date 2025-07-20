package org.kafka.userservice.exception.validation;

import lombok.Getter;

@Getter
public class UserValidationException extends RuntimeException {
    private final UserValidationErrorCode errorCode;

    public UserValidationException(UserValidationErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
