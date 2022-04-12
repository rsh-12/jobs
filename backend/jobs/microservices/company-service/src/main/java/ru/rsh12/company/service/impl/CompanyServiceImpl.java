package ru.rsh12.company.service.impl;
/*
 * Date: 11.04.2022
 * Time: 8:33 AM
 * */

import static java.util.logging.Level.FINE;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import ru.rsh12.company.entity.Company;
import ru.rsh12.company.repository.CompanyRepository;
import ru.rsh12.company.service.CompanyService;

@Slf4j
@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository repository;
    private final Scheduler jdbcScheduler;

    @Autowired
    public CompanyServiceImpl(
            CompanyRepository repository,
            @Qualifier("jdbcScheduler") Scheduler jdbcScheduler) {
        this.repository = repository;
        this.jdbcScheduler = jdbcScheduler;
    }

    @Override
    public Mono<Company> findOne(Integer companyId) {
        log.debug("findOne: gets the company by id={}", companyId);

        return Mono.fromCallable(() -> repository.findById(companyId))
                .log(log.getName(), FINE)
                .log(Thread.currentThread().getName(), FINE)
                .flatMap(Mono::justOrEmpty)
                .subscribeOn(jdbcScheduler);
    }

    @Override
    public Flux<Company> findAll(Pageable pageable) {
        log.debug("findAll: gets list of companies");

        return Mono.fromCallable(() -> repository.findAll(pageable))
                .log(log.getName(), FINE)
                .log(Thread.currentThread().getName(), FINE)
                .flatMapMany(Flux::fromIterable)
                .subscribeOn(jdbcScheduler);
    }

}
