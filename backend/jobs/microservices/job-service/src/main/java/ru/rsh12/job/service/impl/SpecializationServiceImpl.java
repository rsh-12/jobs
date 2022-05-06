package ru.rsh12.job.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import ru.rsh12.job.entity.Specialization;
import ru.rsh12.job.repository.SpecializationRepository;
import ru.rsh12.job.service.SpecializationService;

import java.util.List;

import static java.util.logging.Level.FINE;

@Slf4j
@Service
public class SpecializationServiceImpl implements SpecializationService {

    private final SpecializationRepository repository;
    private final Scheduler jdbcScheduler;

    public SpecializationServiceImpl(
            SpecializationRepository repository,
            @Qualifier("jdbcScheduler") Scheduler jdbcScheduler) {
        this.repository = repository;
        this.jdbcScheduler = jdbcScheduler;
    }


    @Override
    public Flux<Specialization> findAllById(List<Integer> ids) {
        log.debug("findAllById: gets specializations by id");

        return Mono.fromCallable(() -> repository.findByIdIn(ids))
                .log(log.getName(), FINE)
                .log(Thread.currentThread().getName())
                .flatMapMany(Flux::fromIterable)
                .subscribeOn(jdbcScheduler);
    }

}
