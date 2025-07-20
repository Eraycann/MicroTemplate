package org.kafka.userservice.dto.response;

import lombok.*;

/**
 * Başarılı login/register sonrası dönen response.
 * Artık sadece güvenli, minimum veri dönüyoruz.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {
    private Long userId;      // sub alanı ile eşleşiyor
    private String fullName;
    private String token;
}
