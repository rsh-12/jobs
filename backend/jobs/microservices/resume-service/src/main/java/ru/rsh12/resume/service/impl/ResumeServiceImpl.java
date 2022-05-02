package ru.rsh12.resume.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import ru.rsh12.resume.entity.Resume;
import ru.rsh12.resume.repository.ResumeRepository;
import ru.rsh12.resume.service.ResumeService;

import static java.util.logging.Level.FINE;

@Slf4j
@Service
public class ResumeServiceImpl implements ResumeService {

    private final ResumeRepository repository;
    private final Scheduler jdbcScheduler;

    public ResumeServiceImpl(
            ResumeRepository repository,
            @Qualifier("jdbcScheduler") Scheduler jdbcScheduler) {
        this.repository = repository;
        this.jdbcScheduler = jdbcScheduler;
    }

    @Override
    public Mono<Resume> findOne(Integer resumeId) {
        log.debug("findOne: gets the resume by id={}", resumeId);

        return Mono.fromCallable(() -> repository.findById(resumeId))
                .log(log.getName(), FINE)
                .log(Thread.currentThread().getName(), FINE)
                .flatMap(Mono::justOrEmpty)
                .subscribeOn(jdbcScheduler);
    }

    @Override
    public Flux<Resume> findAll(Pageable pageable) {
        log.debug("findAll: gets list of resumes");

        return Mono.fromCallable(() -> repository.findAll(pageable))
                .log(log.getName(), FINE)
                .log(Thread.currentThread().getName(), FINE)
                .flatMapMany(Flux::fromIterable)
                .subscribeOn(jdbcScheduler);
    }

}
