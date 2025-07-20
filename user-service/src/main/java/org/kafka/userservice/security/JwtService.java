package org.kafka.userservice.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.kafka.userservice.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Collectors;

/**
 * ✅ Bu servis, kullanıcı bilgilerine göre JWT Token üretir.
 * 🎯 Token artık userId (subject) ve roles içerecek şekilde sadeleştirilmiştir.
 * 📌 Email gibi hassas veri token içinde yer almaz (güvenlik için).
 */
@Service
public class JwtService {

    @Value("${security.jwt.secret}")
    private String secret;

    @Value("${security.jwt.expiration}")
    private long expirationMs;

    /**
     * Kullanıcıdan JWT Token üretir.
     * @param user Veritabanından gelen kullanıcı nesnesi
     * @return İmzalanmış JWT token
     */
    public String generateToken(User user) {
        return Jwts.builder()
                // 🔐 Token sahibi: Artık kullanıcı ID'si (sub = subject)
                .setSubject(user.getId().toString())

                // 🛡️ Roller claim olarak ekleniyor, yetkilendirme için kullanılacak
                .claim("roles", user.getRoles()
                        .stream()
                        .map(role -> role.getName())
                        .collect(Collectors.toList()))

                // ⏱️ Token oluşturulma zamanı
                .setIssuedAt(new Date())

                // ⏳ Token geçerlilik süresi (örn: 86400000 ms = 1 gün)
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))

                // 🔏 Token imzalanıyor (HMAC SHA-256 + Secret Key)
                .signWith(SignatureAlgorithm.HS256, secret)

                // 🧾 Token string olarak tamamlanıyor
                .compact();
    }
}
