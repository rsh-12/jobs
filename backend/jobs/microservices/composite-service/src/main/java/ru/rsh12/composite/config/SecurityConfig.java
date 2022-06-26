package ru.rsh12.composite.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

@EnableWebFluxSecurity
public class SecurityConfig {

    private final String PATTERN;
    private final String COMPANIES;
    private final String VACANCIES;
    private final String RESUMES;

    public SecurityConfig(@Value("${app.http-pattern}") String pattern,
                          @Value("${app.scope-companies}") String companies,
                          @Value("${app.scope-vacancies}") String vacancies,
                          @Value("${app.scope-resumes}") String resumes) {
        PATTERN = pattern;
        COMPANIES = companies;
        VACANCIES = vacancies;
        RESUMES = resumes;
    }

    @Bean
    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        String[] employers = {PATTERN + "/companies/**", PATTERN + "/jobs/**", PATTERN + "/industries/**"};
        String[] employees = {PATTERN + "/resumes/**"};

        http.authorizeExchange()
                .pathMatchers("/actuator/**").permitAll()
                .pathMatchers("/openapi/**").permitAll()
                .pathMatchers("/webjars/**").permitAll()
                // modify companies, jobs
                .pathMatchers(POST, employers).hasAnyAuthority(COMPANIES, VACANCIES)
                .pathMatchers(PUT, employers).hasAnyAuthority(COMPANIES, VACANCIES)
                .pathMatchers(DELETE, employers).hasAnyAuthority(COMPANIES, VACANCIES)
                // modify resumes
                .pathMatchers(POST, employees).hasAuthority(RESUMES)
                .pathMatchers(PUT, employees).hasAuthority(RESUMES)
                .pathMatchers(DELETE, employees).hasAuthority(RESUMES)
                // read companies, vacancies, resumes
                .pathMatchers(GET, PATTERN + "/**").hasAnyAuthority(COMPANIES, VACANCIES, RESUMES)
                .anyExchange().authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt();

        return http.build();
    }

}
