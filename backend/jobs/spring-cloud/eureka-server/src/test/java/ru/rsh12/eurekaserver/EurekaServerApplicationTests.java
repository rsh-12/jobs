package ru.rsh12.eurekaserver;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(
        webEnvironment = RANDOM_PORT,
        properties = {"management.health.rabbit.enabled=false", "spring.cloud.config.enabled=false"})
class EurekaServerApplicationTests {

    @Test
    void contextLoads() {
    }

}
