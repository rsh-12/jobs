package ru.rsh12.company;
/*
 * Date: 05.04.2022
 * Time: 8:39 AM
 * */

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

public abstract class PostgreSqlTestBase {

    private static final PostgreSQLContainer<?> DB =
            new PostgreSQLContainer<>("postgres:14-alpine");

    static {
        DB.start();
    }

    @DynamicPropertySource
    static void dbProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", DB::getJdbcUrl);
        registry.add("spring.datasource.username", DB::getUsername);
        registry.add("spring.datasource.password", DB::getPassword);
    }

}
