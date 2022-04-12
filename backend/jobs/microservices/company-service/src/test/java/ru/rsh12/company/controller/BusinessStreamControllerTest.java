package ru.rsh12.company.controller;
/*
 * Date: 10.04.2022
 * Time: 12:10 PM
 * */

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import lombok.NonNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.BodyContentSpec;
import reactor.core.publisher.Mono;
import ru.rsh12.api.core.company.dto.BusinessStreamDto;
import ru.rsh12.company.PostgreSqlTestBase;
import ru.rsh12.company.entity.BusinessStream;
import ru.rsh12.company.service.BusinessStreamService;
import ru.rsh12.company.service.mapper.BusinessStreamMapper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BusinessStreamControllerTest extends PostgreSqlTestBase {

    @Autowired
    private WebTestClient client;

    @MockBean
    private BusinessStreamService service;

    @MockBean
    private BusinessStreamMapper mapper;

    private final String API = "/api/v1/industries";

    @Test
    public void getBusinessStream() {
        BusinessStream mockEntity = mock(BusinessStream.class);
        given(service.findOne(anyInt())).willReturn(Mono.just(mockEntity));

        BusinessStreamDto dto = new BusinessStreamDto(1, "Food Products");
        given(mapper.entityToDto(any())).willReturn(dto);

        getAndVerify("/1", HttpStatus.OK)
                .jsonPath("id").isEqualTo(1)
                .jsonPath("name").isEqualTo("Food Products");
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
