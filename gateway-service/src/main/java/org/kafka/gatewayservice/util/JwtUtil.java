package org.kafka.gatewayservice.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

// 🔧 JWT token’ları ile ilgili yardımcı sınıf
@Component
public class JwtUtil {

    // application.yml'den gelen secret-key
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    // Token geçerliliği kontrol edilir (signature valid mi?)
    public void validateToken(String token) {
        Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
    }

    // Token'dan kullanıcı adı (subject) alınır
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    // Token'daki claim'leri döner
    private Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }
}
