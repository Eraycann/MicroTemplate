package org.kafka.security.enums;

// ✅ TokenClaims:
// JWT token içinde kullanılan claim (alan) isimlerini sabit olarak tutar.
// Bu enum sayesinde string yazımı hataları engellenir ve tutarlılık sağlanır.
// Tüm mikroservisler bu enum'u kullanarak token içeriğini aynı şekilde işler.
public enum TokenClaims {
    // Not: USER_ID de kaldırılabilir, çünkü artık jwt.getSubject() üzerinden alıyoruz.
    USER_ID("userId"), // şu an kullanılmıyor, id direkt "sub" (subject) olarak geliyor
    // @Deprecated
    // USER_EMAIL("email"),
    USER_ROLE("roles");

    private final String value;

    TokenClaims(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}