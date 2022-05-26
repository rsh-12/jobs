package ru.rsh12.resume.service;

import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.rsh12.api.core.resume.request.ResumeRequest;
import ru.rsh12.resume.entity.Resume;
import ru.rsh12.resume.entity.SkillSet;

import java.util.List;

public interface ResumeService {

    Mono<Resume> findOne(Integer resumeId);

    Flux<Resume> findAll(Pageable pageable);

    Mono<Void> createResume(ResumeRequest request);

    Mono<Void> updateResume(Integer resumeId, ResumeRequest request);

    Mono<Void> deleteResume(Integer resumeId);

    Flux<SkillSet> findSkillsByIdsIn(List<Integer> skillIds);
}
