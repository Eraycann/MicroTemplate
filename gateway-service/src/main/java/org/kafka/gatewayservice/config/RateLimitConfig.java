package org.kafka.gatewayservice.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class RateLimitConfig {

    // 🌐 IP adresine göre istek sınırlaması uygulamak için kullanılacak anahtar (key)
    @Bean
    public KeyResolver ipKeyResolver() {
        // Her istekten gelen IP adresi, rate limiting için key olarak kullanılır
        return exchange -> Mono.just(
                exchange.getRequest().getRemoteAddress().getAddress().getHostAddress()
        );
    }
}
