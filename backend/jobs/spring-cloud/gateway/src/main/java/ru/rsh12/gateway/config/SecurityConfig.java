package ru.rsh12.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http.csrf()
                .disable()
                .authorizeExchange()
                .pathMatchers("/actuator/**").permitAll()
                .pathMatchers("/eureka/**").permitAll()
                .pathMatchers("/oauth2/**").permitAll()
                .pathMatchers("/login/**").permitAll()
                .pathMatchers("/error/**").permitAll()
                .pathMatchers("/config/**").permitAll()
                .pathMatchers("/openapi/**").permitAll()
                .pathMatchers("/webjars/**").permitAll()
                .anyExchange().authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt();

        return http.build();
    }
}
