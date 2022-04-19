package ru.rsh12.api.core.resume.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.rsh12.api.core.resume.dto.ResumeDto;

@RequestMapping("/api/v1/resumes")
public interface ResumeApi {

    @GetMapping(value = "/{resumeId}", produces = "application/json")
    Mono<ResumeDto> getResume(@PathVariable Integer resumeId);

    @GetMapping(produces = "application/json")
    Flux<ResumeDto> getResumes(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size);

}
