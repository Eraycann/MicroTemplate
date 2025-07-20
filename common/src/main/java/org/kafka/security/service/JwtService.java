package org.kafka.security.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * ✅ JwtService:
 * - JWT token'ı decode eder ve imzasını doğrular.
 * - Token geçerliyse içeriğini (claims) döner.
 * - Bu sınıf, tüm mikroservislerde ortak kullanılır.
 */
@Service
@Slf4j
public class JwtService {

    private final JwtDecoder jwtDecoder;

    /**
     * Constructor'da secret alınarak bir SecretKey oluşturulur.
     * NimbusJwtDecoder, bu key ile token doğrulama yapar.
     *
     * @param secret application.yml dosyasındaki "security.jwt.secret" bilgisidir.
     */
    public JwtService(@Value("${security.jwt.secret}") String secret) {
        // secret string → byte dizisine dönüştürülür → HmacSHA256 algoritmasıyla SecretKeySpec oluşturulur.
        SecretKey secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");

        // NimbusJwtDecoder: JWT imzasını doğrulamak için kullanılan Spring'in default decoder'ıdır.
        this.jwtDecoder = NimbusJwtDecoder.withSecretKey(secretKey).build();
    }

    /**
     * Verilen JWT token geçerliyse decode edilerek claim bilgileri alınır.
     * Geçersizse null döner ve log kaydı alınır.
     *
     * @param token Bearer token (ön ek olmadan)
     * @return Spring'in Jwt objesi (claim'lere erişmek için kullanılır)
     */
    public Jwt validateToken(String token) {
        try {
            return jwtDecoder.decode(token); // İmza doğrulaması + claim decode işlemi
        } catch (JwtException e) {
            log.error("JWT validation failed: {}", e.getMessage()); // Hatalı veya süresi geçmiş token
            return null;
        }
    }
}
