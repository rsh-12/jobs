package ru.rsh12.composite.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import ru.rsh12.api.core.company.api.BusinessStreamApi;
import ru.rsh12.api.core.company.api.CompanyApi;
import ru.rsh12.api.core.company.dto.BusinessStreamDto;
import ru.rsh12.api.core.company.dto.CompanyDto;
import ru.rsh12.api.core.company.request.BusinessStreamRequest;
import ru.rsh12.api.core.company.request.CompanyRequest;
import ru.rsh12.api.core.job.api.JobPostApi;
import ru.rsh12.api.core.job.api.SpecializationApi;
import ru.rsh12.api.core.job.dto.JobPostDto;
import ru.rsh12.api.core.job.dto.SpecializationDto;
import ru.rsh12.api.core.job.request.JobPostRequest;
import ru.rsh12.api.core.resume.api.ResumeApi;
import ru.rsh12.api.core.resume.dto.ResumeDto;
import ru.rsh12.api.core.resume.dto.SkillSetDto;
import ru.rsh12.api.core.resume.request.ResumeRequest;
import ru.rsh12.api.event.Event;
import ru.rsh12.api.exceptions.InvalidInputException;
import ru.rsh12.api.exceptions.NotFoundException;
import ru.rsh12.util.exception.HttpErrorInfo;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static java.util.logging.Level.FINE;
import static ru.rsh12.api.event.Event.Type.CREATE;
import static ru.rsh12.api.event.Event.Type.DELETE;
import static ru.rsh12.api.event.Event.Type.UPDATE;

@Component
public class CompositeIntegration implements
        CompanyApi, ResumeApi, JobPostApi, SpecializationApi, BusinessStreamApi {

    private static final Logger log = LoggerFactory.getLogger(CompositeIntegration.class);

    private static final String COMPANY_SERVICE_URL = "http://company";
    private static final String RESUME_SERVICE_URL = "http://resume";
    private static final String JOB_SERVICE_URL = "http://job";

    private final WebClient webClient;
    private final StreamBridge streamBridge;
    private final Scheduler publishEventScheduler;
    private final ObjectMapper mapper;

    @Autowired
    public CompositeIntegration(
            WebClient.Builder webClient,
            StreamBridge streamBridge,
            @Qualifier("publishEventScheduler") Scheduler publishEventScheduler,
            ObjectMapper mapper) {

        this.webClient = webClient.build();
        this.streamBridge = streamBridge;
        this.publishEventScheduler = publishEventScheduler;
        this.mapper = mapper;
    }

    @Retry(name = "company")
    @TimeLimiter(name = "company")
    @CircuitBreaker(name = "company", fallbackMethod = "getCompanyFallbackValue")
    @Override
    public Mono<CompanyDto> getCompany(Integer companyId) {
        return webClient.get().uri(COMPANY_SERVICE_URL + "/api/v1/companies/{companyId}", companyId)
                .retrieve()
                .bodyToMono(CompanyDto.class)
                .log(log.getName(), FINE)
                .onErrorMap(WebClientResponseException.class, this::handleException);
    }

    @Override
    public Flux<CompanyDto> getCompanies(int page, int size) {
        return webClient.get().uri(COMPANY_SERVICE_URL, uriBuilder -> uriBuilder
                        .path("/api/v1/companies")
                        .queryParam("page", page)
                        .queryParam("size", size)
                        .build())
                .retrieve()
                .bodyToFlux(CompanyDto.class)
                .log(log.getName(), FINE)
                .onErrorMap(ex -> new InvalidInputException("getCompanies failed: " + ex.getMessage()));
    }

    @Override
    public Mono<CompanyDto> createCompany(Integer industryId, CompanyRequest request) {
        return Mono.fromRunnable(
                        () -> sendMessage("companies-out-0", new Event<>(CREATE, industryId, request)))
                .subscribeOn(publishEventScheduler)
                .then(Mono.empty());
    }

    @Override
    public Mono<CompanyDto> updateCompany(Integer industryId, Integer companyId, CompanyRequest request) {
        return Mono.fromRunnable(
                        () -> sendMessage("companies-out-0", new Event<>(UPDATE, companyId, request)))
                .subscribeOn(publishEventScheduler)
                .then(Mono.empty());
    }

    @Override
    public Mono<Boolean> companyExistsById(Integer companyId) {
        return webClient.get().uri(COMPANY_SERVICE_URL, uriBuilder -> uriBuilder
                        .path("/api/v1/companies/" + companyId + "/exists")
                        .build())
                .retrieve()
                .bodyToMono(Boolean.class);
    }

    @Override
    public Mono<Void> deleteCompany(Integer companyId) {
        return Mono.fromRunnable(
                        () -> sendMessage("companies-out-0", new Event<>(DELETE, companyId, null)))
                .subscribeOn(publishEventScheduler)
                .then();
    }

    @Override
    public Mono<JobPostDto> getJobPost(Integer jobPostId) {
        return webClient.get().uri(JOB_SERVICE_URL + "/api/v1/jobs/{jobPostId}", jobPostId)
                .retrieve()
                .bodyToMono(JobPostDto.class)
                .log(log.getName(), FINE)
                .onErrorMap(ex -> new InvalidInputException("getJobPost failed: " + ex.getMessage()));
    }

    @Override
    public Flux<JobPostDto> getJobPosts(int page, int size) {
        return webClient.get().uri(JOB_SERVICE_URL, uriBuilder -> uriBuilder
                        .path("/api/v1/jobs")
                        .queryParam("page", page)
                        .queryParam("size", size)
                        .build())
                .retrieve()
                .bodyToFlux(JobPostDto.class)
                .log(log.getName(), FINE)
                .onErrorMap(ex -> new InvalidInputException("getJobPosts failed: " + ex.getMessage()));
    }

    @Override
    public Flux<JobPostDto> getCompanyJobPosts(Integer companyId, int page, int size) {
        return webClient.get().uri(JOB_SERVICE_URL,
                        uriBuilder -> uriBuilder
                                .path("/api/v1/companies/" + companyId + "/jobs")
                                .queryParam("page", page)
                                .queryParam("size", size)
                                .build())
                .retrieve()
                .bodyToFlux(JobPostDto.class)
                .log(log.getName(), FINE)
                .onErrorMap(ex -> new InvalidInputException("getCompanyJobPosts failed: " + ex.getMessage()));
    }

    @Override
    public Mono<Void> createJobPost(Integer companyId, JobPostRequest request) {
        return Mono.fromRunnable(
                        () -> sendMessage("jobs-out-0", new Event<>(CREATE, null, request)))
                .subscribeOn(publishEventScheduler)
                .then();
    }

    @Override
    public Mono<Void> updateJobPost(Integer companyId, Integer jobPostId, JobPostRequest request) {
        return Mono.fromRunnable(
                        () -> sendMessage("jobs-out-0", new Event<>(UPDATE, jobPostId, request)))
                .subscribeOn(publishEventScheduler)
                .then();
    }

    @Override
    public Mono<Void> deleteJobPost(Integer jobPostId) {
        return Mono.fromRunnable(
                        () -> sendMessage("jobs-out-0", new Event<>(DELETE, jobPostId, null)))
                .subscribeOn(publishEventScheduler)
                .then();
    }

    @Override
    public Flux<SpecializationDto> getSpecializationsById(List<Integer> ids) {
        return webClient.get().uri(JOB_SERVICE_URL, uriBuilder -> uriBuilder
                        .path("/api/v1/specializations")
                        .queryParam("ids", ids)
                        .build())
                .retrieve()
                .bodyToFlux(SpecializationDto.class)
                .log(log.getName(), FINE)
                .onErrorMap(ex -> new InvalidInputException("getSpecializationsById failed: " + ex.getMessage()));
    }

    @Override
    public Mono<ResumeDto> getResume(Integer resumeId) {
        return webClient.get().uri(RESUME_SERVICE_URL + "/api/v1/resumes/{resumeId}", resumeId)
                .retrieve()
                .bodyToMono(ResumeDto.class)
                .log(log.getName(), FINE)
                .onErrorMap(ex -> new InvalidInputException("getResume failed: " + ex.getMessage()));
    }

    @Override
    public Flux<ResumeDto> getResumes(int page, int size) {
        return webClient.get().uri(RESUME_SERVICE_URL, uriBuilder -> uriBuilder
                        .path("/api/v1/resumes")
                        .queryParam("page", page)
                        .queryParam("size", size)
                        .build())
                .retrieve()
                .bodyToFlux(ResumeDto.class)
                .log(log.getName(), FINE)
                .onErrorMap(ex -> new InvalidInputException("getResumes failed: " + ex.getMessage()));
    }

    @Override
    public Mono<Void> createResume(ResumeRequest request) {
        return Mono.fromRunnable(
                        () -> sendMessage("resumes-out-0", new Event<>(CREATE, null, request)))
                .subscribeOn(publishEventScheduler)
                .then();
    }

    @Override
    public Mono<Void> updateResume(Integer resumeId, ResumeRequest request) {
        return Mono.fromRunnable(
                        () -> sendMessage("resumes-out-0", new Event<>(UPDATE, resumeId, request)))
                .subscribeOn(publishEventScheduler)
                .then();
    }

    @Override
    public Mono<Void> deleteResume(Integer resumeId) {
        return Mono.fromRunnable(
                        () -> sendMessage("resumes-out-0", new Event<>(DELETE, resumeId, null)))
                .subscribeOn(publishEventScheduler)
                .then();
    }

    @Override
    public Flux<SkillSetDto> getSkillsByIdsIn(List<Integer> skillIds) {
        return webClient.get().uri(RESUME_SERVICE_URL, uriBuilder -> uriBuilder
                        .path("/api/v1/skills")
                        .queryParam("skillIds", skillIds)
                        .build())
                .retrieve()
                .bodyToFlux(SkillSetDto.class)
                .log(log.getName(), FINE)
                .onErrorMap(ex -> new InvalidInputException("getSkillsByIdsIn failed: " + ex.getMessage()));
    }

    @Override
    public Mono<BusinessStreamDto> getBusinessStream(Integer id) {
        return webClient.get().uri(COMPANY_SERVICE_URL, uriBuilder -> uriBuilder
                        .path("/api/v1/industries/" + id)
                        .build())
                .retrieve()
                .bodyToMono(BusinessStreamDto.class)
                .log(log.getName(), FINE)
                .onErrorMap(ex ->
                        new InvalidInputException("getBusinessStream failed: " + ex.getMessage()));
    }

    @Override
    public Flux<BusinessStreamDto> getBusinessStreams(int page, int size) {
        return webClient.get().uri(COMPANY_SERVICE_URL, uriBuilder -> uriBuilder
                        .path("/api/v1/industries")
                        .queryParam("page", page)
                        .queryParam("size", size)
                        .build())
                .retrieve()
                .bodyToFlux(BusinessStreamDto.class)
                .log(log.getName(), FINE)
                .onErrorMap(ex -> new InvalidInputException("getBusinessStreams failed: " + ex.getMessage()));
    }

    @Override
    public Mono<BusinessStreamDto> createBusinessStream(BusinessStreamRequest request) {
        return Mono.fromRunnable(() ->
                        sendMessage("businessStreams-out-0", new Event<>(CREATE, null, request)))
                .subscribeOn(publishEventScheduler)
                .then(Mono.empty());
    }

    public <T, K> void sendMessage(String bindingName, Event<T, K> event) {
        log.debug("Sending a {} message to {}", event.getEventType(), bindingName);

        Message<Event<T, K>> message = MessageBuilder.withPayload(event)
                .setHeader("partitionKey", event.getKey())
                .build();

        streamBridge.send(bindingName, message);
    }

    private Throwable handleException(Throwable ex) {
        if (!(ex instanceof WebClientResponseException wcre)) {
            log.warn("Got a unexpected error: {}, will rethrow it", ex.toString());
            return ex;
        }

        switch (wcre.getStatusCode()) {
            case NOT_FOUND:
                return new NotFoundException(getErrorMessage(wcre));
            case UNPROCESSABLE_ENTITY:
                return new InvalidInputException(getErrorMessage(wcre));
            default:
                log.warn("Got an unexpected HTTP error: {}, will rethrow it", wcre.getStatusCode());
                log.warn("Error body: {}", wcre.getResponseBodyAsString());
                return ex;
        }
    }

    private String getErrorMessage(WebClientResponseException wcre) {
        try {
            return mapper.readValue(wcre.getResponseBodyAsString(), HttpErrorInfo.class).message();
        } catch (IOException ioex) {
            throw wcre;
        }
    }

    // todo implement caching
    private Mono<CompanyDto> getCompanyFallbackValue(Integer companyId, CallNotPermittedException e) {
        CompanyDto company = new CompanyDto(
                0,
                "Not found",
                "Company '" + companyId + "' not found",
                null,
                "",
                null,
                Collections.emptyList(),
                null,
                null,
                "");

        return Mono.just(company);
    }

}
