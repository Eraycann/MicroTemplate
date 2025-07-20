package org.kafka.userservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger UI yapılandırması.
 * http://localhost:8080/swagger-ui.html üzerinden erişilebilir.
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI userServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("User Service API")
                        .description("JWT üretimi, kullanıcı kayıt/giriş işlemleri")
                        .version("1.0"));
    }
}
