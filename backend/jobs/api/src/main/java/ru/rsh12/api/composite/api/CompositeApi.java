package ru.rsh12.api.composite.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.rsh12.api.composite.dto.CompanyJobPosts;
import ru.rsh12.api.composite.dto.JobPostAggregate;
import ru.rsh12.api.composite.dto.ResumeAggregate;
import ru.rsh12.api.core.company.dto.BusinessStreamDto;
import ru.rsh12.api.core.company.dto.CompanyDto;
import ru.rsh12.api.core.company.request.CompanyRequest;
import ru.rsh12.api.core.company.request.BusinessStreamRequest;
import ru.rsh12.api.core.job.request.JobPostRequest;
import ru.rsh12.api.core.resume.dto.ResumeDto;
import ru.rsh12.api.core.resume.request.ResumeRequest;

import javax.validation.Valid;

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
    Mono<JobPostAggregate> getJobPost(@PathVariable("jobPostId") Integer jobPostId);

    @GetMapping(value = "/api/v1/composite/jobs", produces = "application/json")
    Flux<JobPostAggregate> getJobPosts(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size);

    @PostMapping(
            value = "/api/v1/composite/companies/{companyId}/jobs",
            consumes = "application/json",
            produces = "application/json")
    Mono<Void> createJobPost(
            @PathVariable("companyId") Integer companyId,
            @Valid @RequestBody JobPostRequest request);

    @PutMapping(
            value = "/api/v1/composite/companies/{companyId}/jobs/{jobPostId}",
            consumes = "application/json",
            produces = "application/json")
    Mono<Void> updateJobPost(
            @PathVariable("companyId") Integer companyId,
            @PathVariable("jobPostId") Integer jobPostId,
            @Valid @RequestBody JobPostRequest request);

    @DeleteMapping(value = "/api/v1/composite/companies/{companyId}/jobs/{jobPostId}")
    Mono<Void> deleteJobPost(
            @PathVariable("companyId") Integer companyId,
            @PathVariable("jobPostId") Integer jobPostId);

    @GetMapping(value = "/api/v1/composite/resumes/{resumeId}", produces = "application/json")
    Mono<ResumeAggregate> getResume(@PathVariable("resumeId") Integer resumeId);

    @GetMapping(value = "/api/v1/composite/resumes", produces = "application/json")
    Flux<ResumeDto> getResumes(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size);

    @PostMapping(
            value = "/api/v1/composite/resumes",
            consumes = "application/json",
            produces = "application/json")
    Mono<Void> createResume(@Valid @RequestBody ResumeRequest request);

    @PutMapping(
            value = "/api/v1/composite/resumes/{resumeId}",
            consumes = "application/json",
            produces = "application/json")
    Mono<Void> updateResume(
            @PathVariable("resumeId") Integer resumeId,
            @Valid @RequestBody ResumeRequest request);

    @DeleteMapping(value = "/api/v1/composite/resumes/{resumeId}")
    Mono<Void> deleteResume(@PathVariable("resumeId") Integer resumeId);

    @GetMapping(value = "/api/v1/composite/industries/{id}", produces = "application/json")
    Mono<BusinessStreamDto> getBusinessStream(@PathVariable Integer id);

    @GetMapping(value = "/api/v1/composite/industries", produces = "application/json")
    Flux<BusinessStreamDto> getBusinessStreams(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size);

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(
            value = "/api/v1/composite/industries",
            consumes = "application/json",
            produces = "application/json")
    Mono<BusinessStreamDto> createBusinessStream(@Valid @RequestBody BusinessStreamRequest request);

    @PostMapping(
            value = "/api/v1/composite/industries/{industryId}/companies",
            consumes = "application/json",
            produces = "application/json")
    Mono<CompanyDto> createCompany(
            @PathVariable("industryId") Integer industryId,
            @Valid @RequestBody CompanyRequest request);

    @PutMapping(
            value = "/api/v1/composite/industries/{industryId}/companies/{companyId}",
            consumes = "application/json",
            produces = "application/json")
    Mono<Void> updateCompany(
            @PathVariable("industryId") Integer industryId,
            @PathVariable("companyId") Integer companyId,
            @Valid @RequestBody CompanyRequest request);

    @DeleteMapping(value = "/api/v1/composite/industries/{industryId}/companies/{companyId}")
    Mono<Void> deleteCompany(
            @PathVariable("industryId") Integer industryId,
            @PathVariable("companyId") Integer companyId);

}
