package ru.rsh12.company.controller;
/*
 * Date: 10.04.2022
 * Time: 12:10 PM
 * */

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import lombok.NonNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.BodyContentSpec;
import ru.rsh12.company.PostgreSqlTestBase;
import ru.rsh12.company.entity.BusinessStream;
import ru.rsh12.company.repository.BusinessStreamRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BusinessStreamControllerTest extends PostgreSqlTestBase {

    @Autowired
    private WebTestClient client;

    @Autowired
    private BusinessStreamRepository repository;

    private final String API = "/api/v1/industries";

    private BusinessStream savedEntity;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        assertEquals(0, repository.count());

        savedEntity = repository.save(new BusinessStream("Food Products"));
        assertTrue(repository.count() > 0);
    }

    @Test
    public void getBusinessStreamById() {
        getAndVerify("/" + savedEntity.getId(), HttpStatus.OK)
                .jsonPath("$.id").isEqualTo(savedEntity.getId())
                .jsonPath("$.name").isEqualTo(savedEntity.getName());
    }

    @Test
    public void getBusinessStreams() {
        getAndVerify(HttpStatus.OK)
                .jsonPath("$.length()").isEqualTo(1);
    }

    private BodyContentSpec getAndVerify(String arg, HttpStatus status) {
        return getBodyContentSpec(API + arg, status);
    }

    private BodyContentSpec getAndVerify(HttpStatus status) {
        return getBodyContentSpec(API, status);
    }

    @NonNull
    private BodyContentSpec getBodyContentSpec(String url, HttpStatus status) {
        return client.get()
                .uri(url)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(status)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody();
    }

}
