package ru.rsh12.composite.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.rsh12.api.core.company.api.CompanyApi;
import ru.rsh12.api.core.company.dto.CompanyDto;
import ru.rsh12.api.core.job.api.JobPostApi;
import ru.rsh12.api.core.job.api.SpecializationApi;
import ru.rsh12.api.core.job.dto.JobPostDto;
import ru.rsh12.api.core.job.dto.SpecializationDto;
import ru.rsh12.api.core.resume.api.ResumeApi;
import ru.rsh12.api.core.resume.dto.ResumeDto;
import ru.rsh12.api.exceptions.InvalidInputException;

import java.util.List;
import java.util.logging.Level;

@Component
public class CompositeIntegration implements
        CompanyApi, ResumeApi, JobPostApi, SpecializationApi {

    private static final Logger log = LoggerFactory.getLogger(CompositeIntegration.class);

    private final WebClient webClient;

    private final String companyServiceUrl = "http://localhost:7001/api/v1/companies";
    private final String resumeServiceUrl = "http://localhost:7002/api/v1/resumes";
    private final String jobServiceUrl = "http://localhost:7003/api/v1/jobs";

    public CompositeIntegration(WebClient.Builder webClient) {
        this.webClient = webClient.build();
    }

    // todo replace InvalidInputException
    @Override
    public Mono<CompanyDto> getCompany(Integer companyId) {
        return webClient.get().uri(companyServiceUrl + "/" + companyId)
                .retrieve()
                .bodyToMono(CompanyDto.class)
                .log(log.getName(), Level.FINE)
                .onErrorMap(throwable -> new InvalidInputException("Temporary placeholder :)"));
    }

    // todo replace InvalidInputException
    @Override
    public Flux<CompanyDto> getCompanies(int page, int size) {
        return webClient.get().uri(companyServiceUrl, uriBuilder -> uriBuilder
                        .queryParam("page", page)
                        .queryParam("size", size)
                        .build())
                .retrieve()
                .bodyToFlux(CompanyDto.class)
                .log(log.getName(), Level.FINE)
                .onErrorMap(throwable -> new InvalidInputException("Temporary placeholder :)"));
    }

    @Override
    public Mono<JobPostDto> getJobPost(Integer jobPostId) {
        return webClient.get().uri(jobServiceUrl + "/" + jobPostId)
                .retrieve()
                .bodyToMono(JobPostDto.class)
                .log(log.getName(), Level.FINE)
                .onErrorMap(throwable -> new InvalidInputException("Temporary placeholder :)"));
    }

    @Override
    public Flux<JobPostDto> getJobPosts(int page, int size) {
        return webClient.get().uri(jobServiceUrl, uriBuilder -> uriBuilder
                        .queryParam("page", page)
                        .queryParam("size", size)
                        .build())
                .retrieve()
                .bodyToFlux(JobPostDto.class)
                .log(log.getName(), Level.FINE)
                .onErrorMap(throwable -> new InvalidInputException("Temporary placeholder :)"));
    }

    @Override
    public Flux<JobPostDto> getCompanyJobPosts(Integer companyId, int page, int size) {
        return webClient.get().uri("http://localhost:7003/api/v1/company/" + companyId + "/jobs",
                        uriBuilder -> uriBuilder
                                .queryParam("page", page)
                                .queryParam("size", size)
                                .build())
                .retrieve()
                .bodyToFlux(JobPostDto.class)
                .log(log.getName(), Level.FINE)
                .onErrorMap(throwable -> new InvalidInputException("Temporary placeholder :)"));
    }

    @Override
    public Flux<SpecializationDto> getSpecializationsById(List<Integer> ids) {
        return webClient.get().uri(jobServiceUrl + "/specializations",
                        uriBuilder -> uriBuilder
                                .queryParam("ids", ids)
                                .build())
                .retrieve()
                .bodyToFlux(SpecializationDto.class)
                .log(log.getName(), Level.FINE)
                .onErrorMap(throwable -> new InvalidInputException("Temporary placeholder :)"));
    }

    @Override
    public Mono<ResumeDto> getResume(Integer resumeId) {
        return webClient.get().uri(resumeServiceUrl + "/" + resumeId)
                .retrieve()
                .bodyToMono(ResumeDto.class)
                .log(log.getName(), Level.FINE)
                .onErrorMap(throwable -> new InvalidInputException("Temporary placeholder :)"));
    }

    @Override
    public Flux<ResumeDto> getResumes(int page, int size) {
        return webClient.get().uri(resumeServiceUrl, uriBuilder -> uriBuilder
                        .queryParam("page", page)
                        .queryParam("size", size)
                        .build())
                .retrieve()
                .bodyToFlux(ResumeDto.class)
                .log(log.getName(), Level.FINE)
                .onErrorMap(throwable -> new InvalidInputException("Temporary placeholder :)"));
    }

}
