package ru.rsh12.resume.service;

import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.rsh12.resume.entity.Resume;

public interface ResumeService {

    Mono<Resume> findOne(Integer resumeId);

    Flux<Resume> findAll(Pageable pageable);

}
