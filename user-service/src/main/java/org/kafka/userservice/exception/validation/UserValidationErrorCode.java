package org.kafka.userservice.exception.validation;

// Kullanıcı inputları hatalıysa, kayıt sırasında boş alanlar varsa vs.
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum UserValidationErrorCode {
    EMAIL_ALREADY_EXISTS("USER-VALID-001", "Bu email ile kayıtlı kullanıcı mevcut", HttpStatus.BAD_REQUEST),
    PASSWORD_MISMATCH("USER-VALID-002", "Şifreler uyuşmuyor", HttpStatus.BAD_REQUEST),
    INVALID_CREDENTIALS("USER-VALID-003", "Email veya şifre hatalı", HttpStatus.UNAUTHORIZED);

    private final String code;
    private final String message;
    private final HttpStatus status;

    UserValidationErrorCode(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
