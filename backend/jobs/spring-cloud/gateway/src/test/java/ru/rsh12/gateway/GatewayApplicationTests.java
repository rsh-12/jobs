package ru.rsh12.gateway;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;


@SpringBootTest(webEnvironment = RANDOM_PORT,
        properties = {
                "eureka.client.enabled=false",
                "spring.security.oauth2.resourceserver.jwt.jwk-set-uri=some-url",
                "spring.cloud.config.enabled=false"})
class GatewayApplicationTests {

    @Test
    void contextLoads() {
    }

}
