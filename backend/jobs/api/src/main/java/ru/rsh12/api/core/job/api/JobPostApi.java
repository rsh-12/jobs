package ru.rsh12.api.core.job.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.rsh12.api.core.job.dto.JobPostDto;

public interface JobPostApi {

    @GetMapping(value = "/api/v1/jobs/{jobPostId}", produces = "application/json")
    Mono<JobPostDto> getJobPost(@PathVariable Integer jobPostId);

    @GetMapping(value = "/api/v1/jobs", produces = "application/json")
    Flux<JobPostDto> getJobPosts(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size);

    @GetMapping(value = "/api/v1/company/{companyId}/jobs", produces = "application/json")
    Flux<JobPostDto> getCompanyJobPosts(
            @PathVariable("companyId") Integer companyId,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size);

}
