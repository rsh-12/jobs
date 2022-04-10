package ru.rsh12.company.service.impl;
/*
 * Date: 09.04.2022
 * Time: 10:59 PM
 * */

import static java.util.logging.Level.FINE;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import ru.rsh12.company.entity.BusinessStream;
import ru.rsh12.company.repository.BusinessStreamRepository;
import ru.rsh12.company.service.BusinessStreamService;

@Slf4j
@Service
public class BusinessStreamServiceImpl implements BusinessStreamService {

    private final BusinessStreamRepository repository;
    private final Scheduler jdbcScheduler;

    public BusinessStreamServiceImpl(
            BusinessStreamRepository repository,
            @Qualifier("jdbcScheduler") Scheduler jdbcScheduler) {
        this.repository = repository;
        this.jdbcScheduler = jdbcScheduler;
    }

    @Override
    public Mono<BusinessStream> findOne(Integer id) {
        log.debug("findOne: gets the business stream by id={}", id);

        return Mono.fromCallable(() -> repository.findById(id))
                .log(log.getName(), FINE)
                .log(Thread.currentThread().getName(), FINE)
                .flatMap(Mono::justOrEmpty)
                .subscribeOn(jdbcScheduler);
    }

    @Override
    public Flux<BusinessStream> findAll(Pageable pageable) {
        log.debug("findAll: gets list of business streams");

        return Mono.fromCallable(() -> repository.findAll(pageable))
                .flatMapMany(Flux::fromIterable)
                .log(log.getName(), FINE)
                .log(Thread.currentThread().getName(), FINE)
                .subscribeOn(jdbcScheduler);
    }

}
