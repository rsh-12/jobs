package ru.rsh12.composite.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.rsh12.api.composite.api.CompositeApi;
import ru.rsh12.api.composite.dto.CompanyJobPosts;
import ru.rsh12.api.core.company.dto.CompanyDto;
import ru.rsh12.api.core.job.dto.JobPostDto;
import ru.rsh12.api.core.resume.dto.ResumeDto;

import java.util.List;
import java.util.logging.Level;

@RestController
public class CompositeController implements CompositeApi {

    private static final Logger log = LoggerFactory.getLogger(CompositeController.class);

    private final CompositeIntegration integration;

    @Autowired
    public CompositeController(CompositeIntegration integration) {
        this.integration = integration;
    }

    @Override
    public Mono<CompanyDto> getCompany(Integer companyId) {
        return integration.getCompany(companyId)
                .doOnError(ex -> log.warn("get company by id failed: {}", ex.toString()));
    }

    @Override
    public Flux<CompanyDto> getCompanies(int page, int size) {
        return integration.getCompanies(page, size)
                .doOnError(ex -> log.warn("get companies failed: {}", ex.toString()));
    }

    @Override
    public Mono<CompanyJobPosts> getCompanyJobPosts(Integer companyId, int page, int size) {
        return Mono.zip(values -> createCompanyAggregate((CompanyDto) values[0], (List<JobPostDto>) values[1]),
                        integration.getCompany(companyId),
                        integration.getCompanyJobPosts(companyId, page, size).collectList())
                .doOnError(ex -> log.warn("get company's job posts failed: {}", ex.toString()))
                .log(log.getName(), Level.FINE);
    }

    private CompanyJobPosts createCompanyAggregate(CompanyDto companyDto, List<JobPostDto> jobPostDtos) {
        String companyServiceAddress = companyDto.serviceAddress();
        String jobPostServiceAddress = jobPostDtos.stream().findFirst().map(JobPostDto::serviceAddress).orElse("");

        return new CompanyJobPosts(companyDto, jobPostDtos, companyServiceAddress, jobPostServiceAddress);
    }

    @Override
    public Mono<JobPostDto> getJobPost(Integer jobPostId) {
        return integration.getJobPost(jobPostId)
                .doOnError(ex -> log.warn("get job post by id failed: {}", ex.toString()));
    }

    @Override
    public Flux<JobPostDto> getJobPosts(int page, int size) {
        return integration.getJobPosts(page, size)
                .doOnError(ex -> log.warn("get job posts failed: {}", ex.toString()));
    }

    @Override
    public Mono<ResumeDto> getResume(Integer resumeId) {
        return integration.getResume(resumeId)
                .doOnError(ex -> log.warn("get resume by id failed: {}", ex.toString()));
    }

    @Override
    public Flux<ResumeDto> getResumes(int page, int size) {
        return integration.getResumes(page, size)
                .doOnError(ex -> log.warn("get resumes failed: {}", ex.toString()));
    }

}
