package ru.rsh12.company.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.BodyContentSpec;
import reactor.core.publisher.Mono;
import ru.rsh12.api.core.company.request.CreateCompanyRequest;
import ru.rsh12.api.event.Event;
import ru.rsh12.company.PostgreSqlTestBase;
import ru.rsh12.company.entity.BusinessStream;
import ru.rsh12.company.repository.BusinessStreamRepository;
import ru.rsh12.company.repository.CompanyRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompanyControllerTest extends PostgreSqlTestBase {

    private static final String API = "/api/v1/companies";

    @Autowired
    private WebTestClient client;

    @Autowired
    private CompanyRepository repository;

    @Autowired
    private BusinessStreamRepository businessStreamRepository;

    @Autowired
    @Qualifier("companyMessageProcessor")
    private Consumer<Event<Integer, CreateCompanyRequest>> companyMessageProcessor;

    @BeforeEach
    void setUp() {
        businessStreamRepository.deleteAll();
        repository.deleteAll();

        assertEquals(0, businessStreamRepository.count());
        assertEquals(0, repository.count());
    }

    @Test
    void getCompany_shouldReturnBadRequestResponse_ifCompanyNotFound() {
        getAndVerify(API + "/" + 1, NOT_FOUND)
                .jsonPath("$.message").isEqualTo("Entity not found");
    }

    @Test
    void createCompany_shouldCreateCompany() {
        int industryId = 1;
        CreateCompanyRequest request = createSampleCompany();
        businessStreamRepository.save(new BusinessStream("Art"));

        postAndVerify("/api/v1/industries/" + industryId + "/companies", request, OK)
                .jsonPath("$.name").isEqualTo(request.getName())
                .jsonPath("$.websiteUrl").isEqualTo(request.getWebsiteUrl())
                .jsonPath("$.description").isEqualTo(request.getDescription())
                .jsonPath("$.businessStream").exists()
                .jsonPath("$.images.length()").isEqualTo(request.getImages().size())
                .jsonPath("$.establishmentDate").isEqualTo(request.getEstablishmentDate().toString());
    }

    private CreateCompanyRequest createSampleCompany() {
        return new CreateCompanyRequest()
                .setName("Cat and Dog")
                .setDescription("Food for cats and dogs :)")
                .setEstablishmentDate(LocalDate.now().minusYears(10))
                .setWebsiteUrl("https://catsdogs.io")
                .setImages(List.of("path://to/file1", "path://to/file2"));
    }

    private BodyContentSpec postAndVerify(String url, CreateCompanyRequest request, HttpStatus expectedStatus) {
        return client.post().uri(url)
                .body(Mono.just(request), CreateCompanyRequest.class)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(expectedStatus)
                .expectBody();
    }

    private BodyContentSpec getAndVerify(String url, HttpStatus expectedStatus) {
        return client.get().uri(url)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(expectedStatus)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody();
    }

}
