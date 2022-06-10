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
import ru.rsh12.api.core.company.request.BusinessStreamRequest;
import ru.rsh12.api.core.company.request.CompanyRequest;
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

    @Autowired
    @Qualifier("businessStreamMessageProcessor")
    private Consumer<Event<Integer, BusinessStreamRequest>> businessStreamMessageProcessor;

    @BeforeEach
    void  setUp() {
        repository.deleteAll();
        businessStreamRepository.deleteAll();

        assertEquals(0, businessStreamRepository.count());
        assertEquals(0, repository.count());
    }

    @Test
    void getCompany_shouldReturnBadRequestResponse_ifCompanyNotFound() {
        getAndVerify(API + "/companies/" + 1, NOT_FOUND)
                .jsonPath("$.message").isEqualTo("Entity not found");
    }

    @Test
    void getCompany_shouldReturnOkResponse_ifCompanyFound() {
        sendCreateCompanyEvent();
        getAndVerify(API + "/companies/" + 1, OK);
    }

    private CompanyRequest createSampleCompany() {
        return new CompanyRequest()
                .setName("Cat and Dog")
                .setDescription("Food for cats and dogs :)")
                .setEstablishmentDate(LocalDate.now().minusYears(10))
                .setWebsiteUrl("https://catsdogs.io")
                .setImages(List.of("path://to/file1", "path://to/file2"));
    }

    private BodyContentSpec postAndVerify(String url, CompanyRequest request, HttpStatus expectedStatus) {
        return client.post().uri(url)
                .body(Mono.just(request), CompanyRequest.class)
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

    private void sendCreateCompanyEvent() {
        BusinessStream businessStream = businessStreamRepository.save(new BusinessStream("Animals"));

        getAndVerifyBusinessStream(businessStream.getId(), OK);

        CompanyRequest request = new CompanyRequest(
                "Dog and cat",
                "Food for animals",
                LocalDate.now().minusYears(10),
                "https://dogscats",
                List.of("https://img1"));
        request.setBusinessStreamId(businessStream.getId());

        Event<Integer, CompanyRequest> event = new Event<>(CREATE, null, request);
        companyMessageProcessor.accept(event);
    }

    private void sendCreateBusinessStreamEvent() {
        BusinessStreamRequest request = new BusinessStreamRequest("Animals");
        Event<Integer, BusinessStreamRequest> event = new Event<>(CREATE, null, request);
        businessStreamMessageProcessor.accept(event);
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
