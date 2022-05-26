package ru.rsh12.api.core.job.api;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.rsh12.api.core.job.dto.JobPostDto;
import ru.rsh12.api.core.job.request.JobPostRequest;

import javax.validation.Valid;

public interface JobPostApi {

    @GetMapping(value = "/api/v1/jobs/{jobPostId}", produces = "application/json")
    Mono<JobPostDto> getJobPost(@PathVariable Integer jobPostId);

    @GetMapping(value = "/api/v1/jobs", produces = "application/json")
    Flux<JobPostDto> getJobPosts(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size);

    @GetMapping(value = "/api/v1/companies/{companyId}/jobs", produces = "application/json")
    Flux<JobPostDto> getCompanyJobPosts(
            @PathVariable("companyId") Integer companyId,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size);

    @PostMapping(
            value = "/api/v1/companies/{companyId}/jobs",
            consumes = "application/json",
            produces = "application/json")
    Mono<Void> createJobPost(
            @PathVariable("companyId") Integer companyId,
            @Valid @RequestBody JobPostRequest request);

    @PutMapping(
            value = "/api/v1/companies/{companyId}/jobs/{jobPostId}",
            consumes = "application/json",
            produces = "application/json")
    Mono<Void> updateJobPost(
            @PathVariable("companyId") Integer companyId,
            @PathVariable("jobPostId") Integer jobPostId,
            @Valid @RequestBody JobPostRequest request);

    @DeleteMapping(value = "/api/v1/jobs/{jobPostId}")
    Mono<Void> deleteJobPost(@PathVariable("jobPostId") Integer jobPostId);

}
