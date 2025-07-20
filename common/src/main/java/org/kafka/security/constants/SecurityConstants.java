package org.kafka.security.constants;

// ✅ SecurityConstants:
// Bu sınıf, sabit güvenlik yapılandırmalarını merkezi olarak tutar.
// Örn: Authorization header adı, Bearer prefix, public endpoint'ler gibi değerler burada tanımlanır.
// Ortak kullanımı kolaylaştırmak ve duplication'ı önlemek için common modülde yer alır.
public final class SecurityConstants {

    public static final String AUTH_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String[] PUBLIC_URLS = {
            "/swagger-ui/**",
            "/v3/api-docs/**"
    };

    private SecurityConstants() {}
}