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
import ru.rsh12.api.core.resume.dto.SkillSetDto;
import ru.rsh12.api.core.resume.request.ResumeRequest;
import ru.rsh12.api.exceptions.NotFoundException;
import ru.rsh12.resume.service.ResumeService;
import ru.rsh12.resume.service.mapper.ResumeMapper;
import ru.rsh12.resume.service.mapper.SkillSetMapper;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ResumeController implements ResumeApi {

    private final ResumeService service;
    private final ResumeMapper mapper;

    private final SkillSetMapper skillSetMapper;

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

    @Override
    public Mono<Void> createResume(ResumeRequest request) {
        return service.createResume(request)
                .onErrorMap(throwable -> new NotFoundException("Some predefined data not found"));
    }

    @Override
    public Mono<Void> updateResume(Integer resumeId, ResumeRequest request) {
        return service.updateResume(resumeId, request)
                .onErrorMap(throwable -> new NotFoundException("Some predefined data not found"));
    }

    @Override
    public Mono<Void> deleteResume(Integer resumeId) {
        return service.deleteResume(resumeId);
    }

    @Override
    public Flux<SkillSetDto> getSkillsByIdsIn(List<Integer> skillIds) {
        return service.findSkillsByIdsIn(skillIds)
                .map(skillSetMapper::entityToDto);
    }

}
