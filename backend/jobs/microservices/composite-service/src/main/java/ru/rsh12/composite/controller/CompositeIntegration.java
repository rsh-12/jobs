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
import ru.rsh12.api.core.job.dto.JobPostDto;
import ru.rsh12.api.core.resume.api.ResumeApi;
import ru.rsh12.api.core.resume.dto.ResumeDto;
import ru.rsh12.api.exceptions.InvalidInputException;

import java.util.logging.Level;

@Component
public class CompositeIntegration implements CompanyApi, ResumeApi, JobPostApi {

    private static final Logger log = LoggerFactory.getLogger(CompositeIntegration.class);

    private final WebClient webClient;

    public CompositeIntegration(WebClient.Builder webClient) {
        this.webClient = webClient.build();
    }

    @Override
    public Mono<CompanyDto> getCompany(Integer companyId) {
        return webClient.get().uri("http://localhost:7001/api/v1/companies/" + companyId)
                .retrieve()
                .bodyToMono(CompanyDto.class)
                .log(log.getName(), Level.FINE)
                .onErrorMap(throwable -> new InvalidInputException("Something went wrong, oh no!"));
    }

    @Override
    public Flux<CompanyDto> getCompanies(int page, int size) {
        return null;
    }

    @Override
    public Mono<JobPostDto> getJobPost(Integer jobPostId) {
        return null;
    }

    @Override
    public Flux<JobPostDto> getJobPosts(int page, int size) {
        return null;
    }

    @Override
    public Mono<ResumeDto> getResume(Integer resumeId) {
        return null;
    }

    @Override
    public Flux<ResumeDto> getResumes(int page, int size) {
        return null;
    }

}
