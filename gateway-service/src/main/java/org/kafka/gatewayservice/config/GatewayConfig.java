package org.kafka.gatewayservice.config;

import org.kafka.gatewayservice.filter.JwtAuthenticationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // JwtAuthenticationFilter dependency enjekte edilir
    public GatewayConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    // Tüm route'ların tanımlandığı ana konfigürasyon
    @Bean
    public RouteLocator customRoutes(RouteLocatorBuilder builder) {
        return builder.routes()

                // 🔓 Login ve Register endpoint'lerine JWT filtresi uygulanmaz (token henüz yok)
                .route("user-auth", r -> r.path("/api/users/login", "/api/users/register")
                        .uri("http://localhost:9090"))

                // 🛡️ Diğer kullanıcı endpoint'lerine JWT doğrulama uygulanır
                .route("user-protected", r -> r.path("/api/users/**")
                        .and().not(rp -> rp.path("/api/users/login", "/api/users/register"))
                        .filters(f -> f.filter(jwtAuthenticationFilter)
                                .circuitBreaker(c -> c.setName("userServiceCircuit")
                                        .setFallbackUri("forward:/fallback/user")))
                        .uri("http://localhost:9090"))

                // 💳 Ödeme endpoint'lerine JWT filtresi uygulanır
                .route("payment-service", r -> r.path("/api/payments/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter)
                                .circuitBreaker(c -> c.setName("paymentServiceCircuit")
                                        .setFallbackUri("forward:/fallback/payment")))
                        .uri("http://localhost:9091"))

                .build();
    }
}
