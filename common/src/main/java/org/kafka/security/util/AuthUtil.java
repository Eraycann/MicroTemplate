package org.kafka.security.util;

import org.kafka.security.model.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// ✅ AuthUtil:
// SecurityContext’teki kullanıcıdan token claim bilgilerini (ID, email vs.) çeker.
// @PrePersist gibi lifecycle aşamalarında veya servis katmanında kullanılır.
// Kodun her yerinde direkt SecurityContext'e erişmek yerine bu yardımcı sınıf kullanılır.
/*  ✅ AuthUtil.java
Ne işe yarar?
SecurityContext üzerinden güncel kullanıcı bilgilerini (ID, email, roller vs.) almak için kullanılır.
Servis, Controller veya Entity lifecycle (@PrePersist gibi) işlemlerinde kimlik bilgisine ulaşmayı kolaylaştırır.
* */
public final class AuthUtil {

    private AuthUtil() {}

    public static Optional<UserPrincipal> getCurrentUser() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getPrincipal)
                .filter(UserPrincipal.class::isInstance)
                .map(UserPrincipal.class::cast);
    }

    public static Optional<String> getCurrentUserId() {
        return getCurrentUser().map(UserPrincipal::getId);
    }

    // 🔴 Artık token'da email yok, bu metod gereksiz
    // public static Optional<String> getCurrentUserEmail() {
    //     return getCurrentUser().map(UserPrincipal::getEmail);
    // }

    public static List<String> getCurrentUserRoles() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getAuthorities)
                .map(authorities -> authorities.stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .orElse(List.of());
    }
}

    /*  🧪 Neden Tip Kontrolü (instanceof) Yapılır?
    Çünkü bazı durumlarda getPrincipal() şu türde de dönebilir:
    null
    String (örneğin: "anonymousUser")

    Yanlışlıkla farklı bir tür
    Bu sebeple:
        .map(Authentication::getPrincipal)
        .filter(UserPrincipal.class::isInstance)
        .map(UserPrincipal.class::cast)
    şeklinde güvenli dönüşüm yapılır. Eğer principal bizim istediğimiz UserPrincipal değilse, Optional boş döner. Bu da NPE riskini azaltır.
    * */

/*  Kullanıcı Bilgisine Kod Üzerinden Erişim: AuthUtil
Servis veya Controller içerisinde, kimlik bilgisine ulaşmak için:
Optional<String> userId = AuthUtil.getCurrentUserId();
Optional<String> email = AuthUtil.getCurrentUserEmail();

Bu sınıf, doğrudan SecurityContextHolder erişimini soyutlayarak tekrar kullanılabilir bir yapı sunar.
* */

/*  Kullanımı:
Optional<String> userId = AuthUtil.getCurrentUserId();
Optional<String> email = AuthUtil.getCurrentUserEmail();
List<String> roles = AuthUtil.getCurrentUserRoles();
* */