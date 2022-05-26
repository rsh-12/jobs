package ru.rsh12.composite.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
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

import java.util.List;

import static java.util.logging.Level.FINE;
import static ru.rsh12.api.event.Event.Type.CREATE;
import static ru.rsh12.api.event.Event.Type.DELETE;
import static ru.rsh12.api.event.Event.Type.UPDATE;

@Component
public class CompositeIntegration implements
        CompanyApi, ResumeApi, JobPostApi, SpecializationApi, BusinessStreamApi {

    private static final Logger log = LoggerFactory.getLogger(CompositeIntegration.class);

    private static final String COMPANY_SERVICE_URL = "http://company/api/v1";
    private static final String RESUME_SERVICE_URL = "http://resume/api/v1";
    private static final String JOB_SERVICE_URL = "http://job/api/v1";

    private final WebClient webClient;
    private final StreamBridge streamBridge;
    private final Scheduler publishEventScheduler;

    public CompositeIntegration(
            WebClient.Builder webClient,
            StreamBridge streamBridge,
            @Qualifier("publishEventScheduler") Scheduler publishEventScheduler) {

        this.webClient = webClient.build();
        this.streamBridge = streamBridge;
        this.publishEventScheduler = publishEventScheduler;
    }

    @Override
    public Mono<CompanyDto> getCompany(Integer companyId) {
        return webClient.get().uri(COMPANY_SERVICE_URL + "/companies/" + companyId)
                .retrieve()
                .bodyToMono(CompanyDto.class)
                .log(log.getName(), FINE)
                .onErrorMap(ex -> new InvalidInputException("getCompany failed: " + ex.getMessage()));
    }

    @Override
    public Flux<CompanyDto> getCompanies(int page, int size) {
        return webClient.get().uri(COMPANY_SERVICE_URL, uriBuilder -> uriBuilder
                        .path("/companies")
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
                        .path("/companies/" + companyId + "/exists")
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
        return webClient.get().uri(JOB_SERVICE_URL + "/jobs" + jobPostId)
                .retrieve()
                .bodyToMono(JobPostDto.class)
                .log(log.getName(), FINE)
                .onErrorMap(ex -> new InvalidInputException("getJobPost failed: " + ex.getMessage()));
    }

    @Override
    public Flux<JobPostDto> getJobPosts(int page, int size) {
        return webClient.get().uri(JOB_SERVICE_URL, uriBuilder -> uriBuilder
                        .path("/jobs")
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
                                .path("/companies/" + companyId + "/jobs")
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
                        .path("/specializations")
                        .queryParam("ids", ids)
                        .build())
                .retrieve()
                .bodyToFlux(SpecializationDto.class)
                .log(log.getName(), FINE)
                .onErrorMap(ex -> new InvalidInputException("getSpecializationsById failed: " + ex.getMessage()));
    }

    @Override
    public Mono<ResumeDto> getResume(Integer resumeId) {
        return webClient.get().uri(RESUME_SERVICE_URL + "/resumes" + resumeId)
                .retrieve()
                .bodyToMono(ResumeDto.class)
                .log(log.getName(), FINE)
                .onErrorMap(ex -> new InvalidInputException("getResume failed: " + ex.getMessage()));
    }

    @Override
    public Flux<ResumeDto> getResumes(int page, int size) {
        return webClient.get().uri(RESUME_SERVICE_URL, uriBuilder -> uriBuilder
                        .path("/resumes")
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
                        .path("/skills")
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
                        .path("/industries/" + id)
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
                        .path("/industries")
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

}
