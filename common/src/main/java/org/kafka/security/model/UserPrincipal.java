package org.kafka.security.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

// ✅ UserPrincipal:
// JWT'den çözümlenen kullanıcı bilgilerini temsil eder.
// SecurityContext'e set edilen Authentication objesinin principal'ı olarak atanır.
// Her mikroservis bu principal'dan userId, email ve roller gibi bilgilere erişebilir.
/**
 * ✅ UserPrincipal:
 * Artık sadece ID (subject) ve roller tutuluyor.
 */
@Getter
@AllArgsConstructor
public class UserPrincipal {
    private final String id;
    private final List<String> roles;
}
// JwtSecurityFilter içindeki doFilterInternal() metodunda:
// 1. Token çözümleniyor
// 2. email, roles bilgileri tokendan alınıp UserPrincipal içerisine yazılıyor
// 3. UserPrincipal nesnesi SecurityContextHolder'a set ediliyor.

/*  📌 SecurityContextHolder
Spring Security'nin global güvenlik bağlamıdır. Her HTTP isteği geldiğinde, bu isteğe ait güvenlik bilgileri (kimlik, roller, oturum gibi) SecurityContextHolder içinde tutulur.
* */

/*  ✅ UserPrincipal, UserDetails'i neden implement etmiyor?
    UserDetails interface'ini implement etmiyor.
    Çünkü bu JWT tabanlı ve stateless (durumsuz) bir güvenlik modelinde çoğu UserDetails fonksiyonu gereksiz:
* */

/*  🧠 Peki neden bazı projelerde UserPrincipal implements UserDetails olur?
Çünkü:

Eğer sistemin içinde form-login veya session gibi Spring Security'nin klasik yapılarını kullanıyorsan…

…Spring Security UserDetailsService çağırır, oradan bir UserDetails bekler.

Bu nesne de GrantedAuthority gibi yapıları içerir.

    public class UserPrincipal implements UserDetails {
        private Long id;
        private String email;
        private List<GrantedAuthority> authorities;
        // ... override'lar: getPassword(), getUsername(), isAccountNonExpired() vs.
    }

Ama senin mimarin:

✅ JWT + mikroservis tabanlı → yani stateless → bu kadar detaylı nesnelere gerek yok.


* */

/*  ❓ GrantedAuthority yoksa yetki kontrolü nasıl oluyor?
Senin UserPrincipal'da sadece List<String> roles var. Bu rol isimleri:

JWT token'dan geliyor,

Servis içinde hasRole("ADMIN") gibi ifadelerle yine kullanılabilir,

Hatta istenirse @PreAuthorize("hasRole('ADMIN')") gibi anotasyonlarla da.

Yani:
👉 GrantedAuthority yerine doğrudan String listesiyle kontrol yapılıyor.
Bu da mikroservis mimarilerinde daha hafif ve sade bir yaklaşım.
* */