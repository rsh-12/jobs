package ru.rsh12.company;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(
        webEnvironment = RANDOM_PORT,
        properties = {
                "spring.cloud.stream.default-binder=rabbit",
                "logging.level.ru.rsh12=debug"})
class CompanyServiceApplicationTests extends PostgreSqlTestBase {

    @Test
    void contextLoads() {
    }

}
