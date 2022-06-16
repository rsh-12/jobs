package ru.rsh12.company.controller;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.BodyContentSpec;
import ru.rsh12.api.core.company.request.CompanyRequest;
import ru.rsh12.api.event.Event;
import ru.rsh12.company.PostgreSqlTestBase;
import ru.rsh12.company.entity.BusinessStream;
import ru.rsh12.company.repository.BusinessStreamRepository;
import ru.rsh12.company.repository.CompanyRepository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static ru.rsh12.api.event.Event.Type.CREATE;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "spring.cloud.stream.default-binder=rabbit",
                "logging.level.ru.rsh12=debug",
                "eureka.client.enabled=false",
                "spring.cloud.config.enabled=false"})
public class CompanyControllerTest extends PostgreSqlTestBase {

    private static final String API = "/api/v1";

    @Autowired
    private WebTestClient client;

    @Autowired
    private CompanyRepository repository;

    @Autowired
    private BusinessStreamRepository businessStreamRepository;

    @Autowired
    @Qualifier("companyMessageProcessor")
    private Consumer<Event<Integer, CompanyRequest>> companyMessageProcessor;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        businessStreamRepository.deleteAll();

        assertEquals(0, businessStreamRepository.count());
        assertEquals(0, repository.count());
    }

    @Test
    void getCompany_shouldReturn400_ifCompanyNotFound() {
        getAndVerify(API + "/companies/" + 1, NOT_FOUND)
                .jsonPath("$.message").isEqualTo("Entity not found");
    }

    @Test
    void getCompany_shouldReturn200_ifCompanyFound() {
        assertEquals(0, repository.count());
        BusinessStream industry = createAndGetIndustry();
        sendCreateCompanyEvent(industry, getSampleCompanyRequest());

        assertEquals(1, repository.count());

        getAndVerify(API + "/companies/" + 3, OK)
                .jsonPath("$.name").exists()
                .jsonPath("$.name").isEqualTo("Dog and cat");
    }

    @Test
    void getCompanies_shouldReturnListOfCompanies() {
        assertEquals(0, repository.count());

        BusinessStream industry = createAndGetIndustry();

        sendCreateCompanyEvent(industry, getSampleCompanyRequest());
        sendCreateCompanyEvent(industry, new CompanyRequest(
                "Private Bank",
                "Description",
                LocalDate.of(1970, 4, 12),
                "some.website.com",
                Collections.emptyList()));

        assertEquals(2, repository.count());
    }

    private BodyContentSpec getAndVerify(String url, HttpStatus expectedStatus) {
        return client.get().uri(url)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(expectedStatus)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody();
    }

    private void sendCreateCompanyEvent(BusinessStream businessStream, CompanyRequest request) {
        request.setBusinessStreamId(businessStream.getId());

        Event<Integer, CompanyRequest> event = new Event<>(CREATE, null, request);
        companyMessageProcessor.accept(event);
    }

    @NotNull
    private BusinessStream createAndGetIndustry() {
        String businessStreamName = "Finance";
        BusinessStream businessStream = businessStreamRepository.save(new BusinessStream(businessStreamName));

        getAndVerifyBusinessStream(businessStream.getId(), OK)
                .jsonPath("$.name").exists()
                .jsonPath("$.name").isEqualTo(businessStreamName);

        return businessStream;
    }

    @NotNull
    private CompanyRequest getSampleCompanyRequest() {
        return new CompanyRequest(
                "Dog and cat",
                "Food for animals",
                LocalDate.now().minusYears(10),
                "https://dogscats",
                List.of("https://img1"));
    }

    private WebTestClient.BodyContentSpec getAndVerifyBusinessStream(
            Integer businessStreamId,
            HttpStatus expectedStatus) {

        return client.get()
                .uri(API + "/industries/" + businessStreamId)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(expectedStatus)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody();
    }

}
