package ru.rsh12.resume.controller;
/*
 * Date: 03.04.2022
 * Time: 11:28 AM
 * */

import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.rsh12.api.core.resume.api.ResumeApi;
import ru.rsh12.api.core.resume.dto.ResumeDto;

@RestController
public class ResumeController implements ResumeApi {

    @Override
    public Mono<ResumeDto> getResume(Integer resumeId) {
        return null;
    }

    @Override
    public Flux<ResumeDto> getResumes(int page, int size) {
        return null;
    }

}
