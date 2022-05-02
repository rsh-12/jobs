package ru.rsh12.resume.controller;
/*
 * Date: 03.04.2022
 * Time: 11:28 AM
 * */

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.rsh12.api.core.resume.api.ResumeApi;
import ru.rsh12.api.core.resume.dto.ResumeDto;
import ru.rsh12.resume.service.ResumeService;
import ru.rsh12.resume.service.mapper.ResumeMapper;

@RestController
@RequiredArgsConstructor
public class ResumeController implements ResumeApi {

    private final ResumeService service;
    private final ResumeMapper mapper;

    @Override
    public Mono<ResumeDto> getResume(Integer resumeId) {
        return service.findOne(resumeId).map(mapper::entityToDto);
    }

    @Override
    public Flux<ResumeDto> getResumes(int page, int size) {
        page = Math.max(page, 0);
        size = Math.max(size, 1);

        return service.findAll(PageRequest.of(page, size))
                .map(mapper::entityToDto);
    }

}
