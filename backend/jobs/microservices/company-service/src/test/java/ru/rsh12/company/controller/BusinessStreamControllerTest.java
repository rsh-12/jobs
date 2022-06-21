package ru.rsh12.company.controller;
/*
 * Date: 10.04.2022
 * Time: 12:10 PM
 * */

import lombok.NonNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.BodyContentSpec;
import reactor.core.publisher.Mono;
import ru.rsh12.api.core.company.request.BusinessStreamRequest;
import ru.rsh12.api.core.company.request.CompanyRequest;
import ru.rsh12.company.PostgreSqlTestBase;
import ru.rsh12.company.entity.BusinessStream;
import ru.rsh12.company.repository.BusinessStreamRepository;
import ru.rsh12.company.repository.CompanyImageRepository;
import ru.rsh12.company.repository.CompanyRepository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest(
        webEnvironment = WebEnvironment.RANDOM_PORT,
        properties = {
                "spring.cloud.stream.default-binder=rabbit",
                "logging.level.ru.rsh12=debug",
                "eureka.client.enabled=false",
                "spring.cloud.config.enabled=false"})
public class BusinessStreamControllerTest extends PostgreSqlTestBase {

    @Autowired
    private WebTestClient client;

    @Autowired
    private BusinessStreamRepository repository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyImageRepository companyImageRepository;

    private static final String API = "/api/v1/industries";

    @BeforeEach
    void setUp() {
        companyImageRepository.deleteAll();
        companyRepository.deleteAll();
        repository.deleteAll();

        assertEquals(0, repository.count());
        assertEquals(0, companyRepository.count());
        assertEquals(0, companyImageRepository.count());
    }

    @Test
    void getBusinessStream_shouldReturnBusinessStreamDto() {
        String name = "Art";
        BusinessStream businessStream = repository.save(new BusinessStream(name));
        assertNotNull(businessStream);

        getAndVerify("/" + businessStream.getId(), HttpStatus.OK);
    }

    @Test
    void createBusinessStream_shouldReturnBadRequestErrorResponse_ifNameIsNotValid() {
        postAndVerify(API, new BusinessStreamRequest(""), HttpStatus.BAD_REQUEST);
    }

    @Test
    void createBusinessStream_shouldReturnBadRequestErrorResponse_ifCreateCompanyRequestInvalid() {
        BusinessStreamRequest request = new BusinessStreamRequest("Art")
                .setCompanies(Collections.singleton(new CompanyRequest()
                        .setDescription("Lorem ipsum")));
        postAndVerify(API, request, HttpStatus.BAD_REQUEST);
    }

    @Test
    void createBusinessStream_shouldCreateBusinessStream_withCompaniesAndImages() {
        int size = 3;
        Set<CompanyRequest> createCompanyRequests = createCompanyRequests(size);
        BusinessStreamRequest request = new BusinessStreamRequest("Art")
                .setCompanies(createCompanyRequests);

        postAndVerify(API, request, HttpStatus.CREATED);

        assertEquals(1, repository.count());
        assertEquals(size, companyRepository.count());
        assertEquals(size, companyImageRepository.count());
    }

    private BodyContentSpec postAndVerify(String url, BusinessStreamRequest request, HttpStatus expectedStatus) {
        return client.post()
                .uri(url)
                .body(Mono.just(request), BusinessStreamRequest.class)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(expectedStatus)
                .expectBody();
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

    public Set<CompanyRequest> createCompanyRequests(int size) {
        return IntStream.rangeClosed(1, size)
                .mapToObj(i -> new CompanyRequest(
                        "Company " + i,
                        "Company description " + i,
                        LocalDate.now().minusYears(i),
                        "http://some.url" + i,
                        Collections.singletonList("file://path/to/file" + i)))
                .collect(Collectors.toSet());
    }

}
