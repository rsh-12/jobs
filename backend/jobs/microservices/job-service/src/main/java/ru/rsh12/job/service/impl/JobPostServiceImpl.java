package ru.rsh12.job.service.impl;
/*
 * Date: 18.04.2022
 * Time: 10:35 AM
 * */

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import ru.rsh12.job.entity.JobPost;
import ru.rsh12.job.repository.JobPostRepository;
import ru.rsh12.job.service.JobPostService;

import static java.util.logging.Level.FINE;

@Slf4j
@Service
public class JobPostServiceImpl implements JobPostService {

    private final JobPostRepository repository;
    private final Scheduler jdbcScheduler;

    @Autowired
    public JobPostServiceImpl(
            JobPostRepository repository,
            @Qualifier("jdbcScheduler") Scheduler jdbcScheduler) {
        this.repository = repository;
        this.jdbcScheduler = jdbcScheduler;
    }

    @Override
    public Mono<JobPost> findOne(Integer jobPostId) {
        log.debug("findOne: gets the job post by id={}", jobPostId);

        return Mono.fromCallable(() -> repository.findById(jobPostId))
                .log(log.getName(), FINE)
                .log(Thread.currentThread().getName(), FINE)
                .flatMap(Mono::justOrEmpty)
                .subscribeOn(jdbcScheduler);
    }

    @Override
    public Flux<JobPost> findAll(Pageable pageable) {
        log.debug("findAll: gets list of job posts");

        return Mono.fromCallable(() -> repository.findAll(pageable))
                .log(log.getName(), FINE)
                .log(Thread.currentThread().getName(), FINE)
                .flatMapMany(Flux::fromIterable)
                .subscribeOn(jdbcScheduler);
    }

    @Override
    public Flux<JobPost> findCompanyJobPosts(Integer companyId, Pageable pageable) {
        return Mono.fromCallable(() -> repository.findByPostedById(companyId, pageable))
                .log(log.getName(), FINE)
                .log(Thread.currentThread().getName())
                .flatMapMany(Flux::fromIterable)
                .subscribeOn(jdbcScheduler);
    }

}
