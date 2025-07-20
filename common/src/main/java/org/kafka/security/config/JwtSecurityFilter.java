package org.kafka.security.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.kafka.security.constants.SecurityConstants;
import org.kafka.security.enums.TokenClaims;
import org.kafka.security.model.UserPrincipal;
import org.kafka.security.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

// ✅ JwtSecurityFilter:
// Her gelen HTTP isteğinde Authorization header’daki JWT’yi doğrular.
// Token geçerliyse kullanıcı bilgilerini SecurityContext'e aktarır.
// `SecurityContextHolder` üzerinden mikroservislerde kimlik bilgilerine erişim sağlanır.
// Ortak filtre yapısıdır, tüm servislerde `SecurityFilterChain`'e eklenmelidir.
/**
 * ✅ JwtSecurityFilter:
 *
 * Ortak security katmanında yer alır ve mikroservislerin her isteğinde çalışır.
 * Görevi, gelen istekten JWT token'ı alıp doğrulamak, ardından kullanıcı bilgilerini
 * Spring Security'nin SecurityContext'ine aktarmaktır.
 *
 * 🔐 Akış:
 * 1. Authorization header'dan Bearer token alınır.
 * 2. Token doğrulanır.
 * 3. Email ve roller gibi claim'ler ayrıştırılır.
 * 4. UserPrincipal nesnesi oluşturulur.
 * 5. Authentication objesi SecurityContext'e atanır.
 */
@Component
@RequiredArgsConstructor
public class JwtSecurityFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Header'da Authorization var mı kontrolü yapılır.
        final String authHeader = request.getHeader(SecurityConstants.AUTH_HEADER);
        if (authHeader == null || !authHeader.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            throw new AuthenticationCredentialsNotFoundException("Authorization header eksik veya 'Bearer' ile başlamıyor.");
        }

        // 2. Bearer kısmı atılarak raw token alınır.
        final String token = authHeader.substring(SecurityConstants.TOKEN_PREFIX.length());

        // 3. Token doğrulanır (signature, expiration vs.).
        Jwt jwt = jwtService.validateToken(token);
        if (jwt == null) {
            throw new AuthenticationCredentialsNotFoundException("JWT doğrulaması başarısız.");
        }

        // 🔐 Token'dan userId artık subject (sub) alanından geliyor
        String userId = jwt.getSubject();  // JWT'nin "sub" alanı

        // 🔽 Token'da email artık yok
        // String email = jwt.getClaimAsString(TokenClaims.USER_EMAIL.getValue());

        List<String> roles = jwt.getClaimAsStringList(TokenClaims.USER_ROLE.getValue());

        // 5. Roller Spring Security authority tipine çevrilir.
        var authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        // 6. Authentication objesi oluşturulmadan önce, user bilgisi taşıyan özel bir sınıf oluşturulur.

        /**
         * ✅ Neden UserPrincipal kullandık?
         *
         * - Spring Security varsayılan olarak sadece username taşır.
         * - Biz ise hem email, hem roller, hem de (varsa) userId gibi bilgileri taşımak istiyoruz.
         * - UserPrincipal bunu temsil eden bir custom model'dir.
         * - Servis katmanlarında `AuthUtil.getCurrentUserId()` gibi yöntemlerle erişim sağlar.
         */
        // ✅ Yeni UserPrincipal sadece id ve roller içeriyor
        var principal = new UserPrincipal(userId, roles);

        // 7. Authentication nesnesi oluşturulup SecurityContext'e atanır.
        var authToken = new UsernamePasswordAuthenticationToken(principal, null, authorities);
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authToken);

        // 8. Filtre zincirine devam edilir.
        filterChain.doFilter(request, response);
    }
}
/*  📌 Ek Notlar:
| Yapı                    | Açıklama                                                                                              |
| ----------------------- | ----------------------------------------------------------------------------------------------------- |
| `TokenClaims`           | Tüm claim key'lerini enum olarak sabitledik (örn. `"roles"`, `"email"` vs.)                           |
| `JwtService`            | Ortak JWT token doğrulama servisidir. Signature kontrolü ve expiration gibi validasyonları yapar.     |
| `UserPrincipal`         | JWT'den gelen bilgileri taşıyan özel kullanıcı sınıfı. `AuthUtil` üzerinden servislerde erişilebilir. |
| `SecurityContextHolder` | Spring Security'nin thread-local yapısıdır. Kimlik doğrulama burada tutulur.                          |
* */