package ru.rsh12.job.controller;
/*
 * Date: 03.04.2022
 * Time: 11:24 AM
 * */

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.rsh12.api.core.job.api.JobPostApi;
import ru.rsh12.api.core.job.dto.JobPostDto;

@RestController
@RequiredArgsConstructor
public class JobPostController implements JobPostApi {

    @Override
    public Mono<JobPostDto> getJobPost(Integer jobPostId) {
        return null;
    }

    @Override
    public Flux<JobPostDto> getJobPosts(int page, int size) {
        return null;
    }

}
