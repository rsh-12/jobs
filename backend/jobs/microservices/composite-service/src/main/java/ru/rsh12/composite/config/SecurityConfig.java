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

    public SecurityConfig(@Value("${http-pattern}") String pattern,
                          @Value("${scope-companies}") String companies,
                          @Value("${scope-vacancies}") String vacancies,
                          @Value("${scope-resumes}") String resumes) {
        PATTERN = pattern;
        COMPANIES = companies;
        VACANCIES = vacancies;
        RESUMES = resumes;
    }

    @Bean
    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange()
                .pathMatchers("/actuator/**").permitAll()
                // modify companies, jobs
                .pathMatchers(POST, PATTERN + "/companies/**", PATTERN + "/jobs/**").hasAnyAuthority(COMPANIES, VACANCIES)
                .pathMatchers(PUT, PATTERN + "/companies/**", PATTERN + "/jobs/**").hasAnyAuthority(COMPANIES, VACANCIES)
                .pathMatchers(DELETE, PATTERN + "/companies/**", PATTERN + "/jobs/**").hasAnyAuthority(COMPANIES, VACANCIES)
                // modify resumes
                .pathMatchers(POST, PATTERN + "/resumes/**").hasAuthority(RESUMES)
                .pathMatchers(PUT, PATTERN + "/resumes/**").hasAuthority(RESUMES)
                .pathMatchers(DELETE, PATTERN + "/resumes/**").hasAuthority(RESUMES)
                // read companies, vacancies, resumes
                .pathMatchers(GET, PATTERN + "/**").hasAnyAuthority(COMPANIES, VACANCIES, RESUMES)
                .anyExchange().authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt();

        return http.build();
    }

}
