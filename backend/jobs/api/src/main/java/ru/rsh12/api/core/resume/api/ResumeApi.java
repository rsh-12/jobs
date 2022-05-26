package ru.rsh12.api.core.resume.api;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.rsh12.api.core.resume.dto.ResumeDto;
import ru.rsh12.api.core.resume.dto.SkillSetDto;
import ru.rsh12.api.core.resume.request.ResumeRequest;

import javax.validation.Valid;
import java.util.List;

public interface ResumeApi {

    @GetMapping(value = "/api/v1/resumes/{resumeId}", produces = "application/json")
    Mono<ResumeDto> getResume(@PathVariable Integer resumeId);

    @GetMapping(value = "/api/v1/resumes", produces = "application/json")
    Flux<ResumeDto> getResumes(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size);

    @PostMapping(value = "/api/v1/resumes", consumes = "application/json")
    Mono<Void> createResume(@Valid @RequestBody ResumeRequest request);

    @PutMapping(value = "/api/v1/resumes/{resumeId}", consumes = "application/json")
    Mono<Void> updateResume(
            @PathVariable("resumeId") Integer resumeId,
            @Valid @RequestBody ResumeRequest request);

    @DeleteMapping(value = "/api/v1/resumes/{resumeId}")
    Mono<Void> deleteResume(@PathVariable("resumeId") Integer resumeId);

    @GetMapping(value = "/api/v1/skills", produces = "application/json")
    Flux<SkillSetDto> getSkillsByIdsIn(@RequestParam("skillIds") List<Integer> skillIds);

}
