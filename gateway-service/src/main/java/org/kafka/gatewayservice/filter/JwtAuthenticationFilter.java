package org.kafka.gatewayservice.filter;

import org.kafka.gatewayservice.util.JwtUtil;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

// 🔐 JWT doğrulaması yapan Gateway filtresi
@Component
public class JwtAuthenticationFilter implements GatewayFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders headers = exchange.getRequest().getHeaders();

        // Authorization header mevcut değilse 401 döndür
        if (!headers.containsKey(HttpHeaders.AUTHORIZATION)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // Token’ı “Bearer ” kısmını çıkararak al
        String token = headers.getFirst(HttpHeaders.AUTHORIZATION).replace("Bearer ", "");

        try {
            // Token doğrulaması yapılır
            jwtUtil.validateToken(token);

            // İsteğe bağlı olarak kullanıcı adı çıkarılabilir
            String username = jwtUtil.extractUsername(token);

        } catch (Exception e) {
            // Token geçersizse 401 Unauthorized
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // Token geçerliyse diğer filtrelere devam et
        return chain.filter(exchange);
    }
}
