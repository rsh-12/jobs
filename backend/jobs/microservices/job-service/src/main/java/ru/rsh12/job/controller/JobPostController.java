package ru.rsh12.job.controller;
/*
 * Date: 03.04.2022
 * Time: 11:24 AM
 * */

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.rsh12.api.core.job.api.JobPostApi;
import ru.rsh12.api.core.job.dto.JobPostDto;
import ru.rsh12.job.service.JobPostService;
import ru.rsh12.job.service.mapper.JobPostMapper;

@RestController
@RequiredArgsConstructor
public class JobPostController implements JobPostApi {

    private final JobPostService service;
    private final JobPostMapper mapper;

    @Override
    public Mono<JobPostDto> getJobPost(Integer jobPostId) {
        return service.findOne(jobPostId)
                .map(mapper::entityToDto);
    }

    @Override
    public Flux<JobPostDto> getJobPosts(int page, int size) {
        page = Math.max(page, 0);
        size = Math.max(size, 1);

        return service.findAll(PageRequest.of(page, size))
                .map(mapper::entityToDto);
    }

}
