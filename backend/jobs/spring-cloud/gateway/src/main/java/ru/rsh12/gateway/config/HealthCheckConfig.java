package ru.rsh12.gateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.CompositeReactiveHealthContributor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.ReactiveHealthContributor;
import org.springframework.boot.actuate.health.ReactiveHealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.logging.Level.FINE;

@Configuration
public class HealthCheckConfig {

    private static final Logger log = LoggerFactory.getLogger(HealthCheckConfig.class);

    private final WebClient webClient;

    @Autowired
    public HealthCheckConfig(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    @Bean
    ReactiveHealthContributor healthCheckMicroservices() {
        final Map<String, ReactiveHealthIndicator> registry = new LinkedHashMap<>() {{
            put("composite", () -> getHealth("http://composite"));
            put("company", () -> getHealth("http://company"));
            put("resume", () -> getHealth("http://resume"));
            put("job", () -> getHealth("http://job"));
        }};

        return CompositeReactiveHealthContributor.fromMap(registry);
    }

    private Mono<Health> getHealth(String baseUrl) {
        String url = baseUrl + "/actuator/health";

        log.debug("Setting up a call to the Health on API on URL: {}", url);

        return webClient.get().uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .map(s -> new Health.Builder().up().build())
                .onErrorResume(ex -> Mono.just(new Health.Builder().down(ex).build()))
                .log(log.getName(), FINE);
    }

}
