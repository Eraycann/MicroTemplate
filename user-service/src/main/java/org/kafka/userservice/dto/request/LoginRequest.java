package org.kafka.userservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * Kullanıcı girişi için gerekli veriler.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {

    @NotBlank(message = "Email zorunludur.")
    @Email(message = "Geçerli bir email giriniz.")
    private String email;

    @NotBlank(message = "Şifre zorunludur.")
    private String password;
}
