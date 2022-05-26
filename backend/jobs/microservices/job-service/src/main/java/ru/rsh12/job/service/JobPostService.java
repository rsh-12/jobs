package ru.rsh12.job.service;

import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.rsh12.api.core.job.request.JobPostRequest;
import ru.rsh12.job.entity.JobPost;

public interface JobPostService {

    Mono<JobPost> findOne(Integer jobPostId);

    Flux<JobPost> findAll(Pageable pageable);

    Flux<JobPost> findCompanyJobPosts(Integer companyId, Pageable pageable);

    Mono<Void> createJobPost(Integer companyId, JobPostRequest request);

    Mono<Void> updateJobPost(Integer companyId, Integer jobPostId, JobPostRequest request);

    Mono<Void> deleteJobPost(Integer jobPostId);
}
