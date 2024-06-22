package spring_security_shield.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOriginPatterns("*") // Permite solicitudes de cualquier origen
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Permite los métodos especificados
                        .allowedHeaders("*") // Permite cualquier encabezado
                        .allowCredentials(true); // Permite el envío de cookies y credenciales
            }
        };
    }
}