package org.kafka.userservice.exception.domain;

// Kullanıcı veritabanında bulunamazsa, rol hatası gibi durumlarda kullanılır.
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum UserDomainErrorCode {
    USER_NOT_FOUND("USER-DOMAIN-001", "Kullanıcı bulunamadı", HttpStatus.NOT_FOUND),
    ROLE_NOT_FOUND("USER-DOMAIN-002", "Rol bulunamadı", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;
    private final String message;
    private final HttpStatus status;

    UserDomainErrorCode(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
