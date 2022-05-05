package ru.rsh12.api.composite.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.rsh12.api.composite.dto.CompanyJobPosts;
import ru.rsh12.api.core.company.dto.CompanyDto;
import ru.rsh12.api.core.job.dto.JobPostDto;
import ru.rsh12.api.core.resume.dto.ResumeDto;

public interface CompositeApi {

    @GetMapping(value = "/api/v1/composite/companies/{companyId}", produces = "application/json")
    Mono<CompanyDto> getCompany(@PathVariable("companyId") Integer companyId);

    @GetMapping(value = "/api/v1/composite/companies", produces = "application/json")
    Flux<CompanyDto> getCompanies(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size);

    @GetMapping(value = "/api/v1/composite/companies/{companyId}/jobs")
    Mono<CompanyJobPosts> getCompanyJobPosts(
            @PathVariable("companyId") Integer companyId,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size);

    @GetMapping(value = "/api/v1/composite/jobs/{jobPostId}", produces = "application/json")
    Mono<JobPostDto> getJobPost(@PathVariable("jobPostId") Integer jobPostId);

    @GetMapping(value = "/api/v1/composite/jobs", produces = "application/json")
    Flux<JobPostDto> getJobPosts(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size);

    @GetMapping(value = "/api/v1/composite/resumes/{resumeId}", produces = "application/json")
    Mono<ResumeDto> getResume(@PathVariable("resumeId") Integer resumeId);

    @GetMapping(value = "/api/v1/composite/resumes", produces = "application/json")
    Flux<ResumeDto> getResumes(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size);

}
