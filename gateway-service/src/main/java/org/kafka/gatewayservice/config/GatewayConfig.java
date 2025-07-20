package org.kafka.gatewayservice.config;

import org.kafka.gatewayservice.filter.JwtAuthenticationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public GatewayConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public RouteLocator customRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-service", r -> r.path("/api/users/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter)
                                .circuitBreaker(c -> c.setName("userServiceCircuit")
                                        .setFallbackUri("forward:/fallback/user")))
                        .uri("http://localhost:9090"))
                .route("payment-service", r -> r.path("/api/payments/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter)
                                .circuitBreaker(c -> c.setName("paymentServiceCircuit")
                                        .setFallbackUri("forward:/fallback/payment")))
                        .uri("http://localhost:9091"))
                .build();
    }
}
