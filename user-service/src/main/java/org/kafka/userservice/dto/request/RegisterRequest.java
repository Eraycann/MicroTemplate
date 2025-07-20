package org.kafka.userservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Yeni kullanıcı kaydı için gerekli veriler.
 * Validasyon içerir.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {

    @NotBlank(message = "Email zorunludur.")
    @Email(message = "Geçerli bir email adresi giriniz.")
    private String email;

    @NotBlank(message = "Şifre zorunludur.")
    @Size(min = 6, message = "Şifre en az 6 karakter olmalıdır.")
    private String password;

    @NotBlank(message = "Ad soyad zorunludur.")
    private String fullName;
}
