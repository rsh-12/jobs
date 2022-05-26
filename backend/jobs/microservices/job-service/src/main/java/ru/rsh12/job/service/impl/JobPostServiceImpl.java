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
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import ru.rsh12.api.core.job.dto.JobPostSkillSetDto;
import ru.rsh12.api.core.job.request.JobPostRequest;
import ru.rsh12.job.entity.JobLocation;
import ru.rsh12.job.entity.JobPost;
import ru.rsh12.job.entity.JobPostSkillSet;
import ru.rsh12.job.entity.JobPostSkillSetId;
import ru.rsh12.job.entity.JobType;
import ru.rsh12.job.entity.Specialization;
import ru.rsh12.job.repository.JobPostRepository;
import ru.rsh12.job.repository.JobPostSkillSetRepository;
import ru.rsh12.job.service.JobPostService;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.util.logging.Level.FINE;

@Slf4j
@Service
@Transactional
public class JobPostServiceImpl implements JobPostService {

    private final JobPostRepository repository;
    private final Scheduler jdbcScheduler;

    @Autowired
    private JobPostSkillSetRepository jobPostSkillSetRepository;

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
        log.debug("findCompanyJobPosts: gets list of job posts by company id");

        return Mono.fromCallable(() -> repository.findByPostedById(companyId, pageable))
                .log(log.getName(), FINE)
                .log(Thread.currentThread().getName())
                .flatMapMany(Flux::fromIterable)
                .subscribeOn(jdbcScheduler);
    }

    @Override
    public Mono<Void> createJobPost(Integer companyId, JobPostRequest request) {
        log.debug("createJobPost: creates new JobPost");

        JobPost jobPost = toJobPost(request);
        return Mono.fromRunnable(() -> {
                    JobPost entity = repository.save(jobPost);
                    CompletableFuture.runAsync(() -> saveJobPostSkills(entity, request.getSkills()));
                })
                .doOnError(throwable -> log.warn("createJobPost failed {}", throwable.getMessage()))
                .log(log.getName(), FINE)
                .log(Thread.currentThread().getName(), FINE)
                .subscribeOn(jdbcScheduler)
                .then();
    }

    @Override
    public Mono<Void> updateJobPost(Integer companyId, Integer jobPostId, JobPostRequest request) {
        log.debug("updateJobPost: creates JobPost by id {}", jobPostId);

        return Mono.fromRunnable(() -> repository.findById(jobPostId)
                        .map(jobPost -> {
                            JobPost entity = toJobPost(jobPost, request);
                            CompletableFuture.runAsync(() -> saveJobPostSkills(entity, request.getSkills()));
                            return repository.save(entity);
                        }))
                .doOnError(throwable -> log.warn("updateJobPost failed {}", throwable.getMessage()))
                .log(log.getName(), FINE)
                .log(Thread.currentThread().getName(), FINE)
                .subscribeOn(jdbcScheduler)
                .then();
    }

    @Override
    public Mono<Void> deleteJobPost(Integer jobPostId) {
        log.debug("deleteJobPost: deletes JobPost by id {}", jobPostId);

        return Mono.fromRunnable(() -> repository.deleteById(jobPostId))
                .doOnError(throwable -> log.warn("deleteJobPost failed {}", throwable.getMessage()))
                .log(log.getName(), FINE)
                .log(Thread.currentThread().getName(), FINE)
                .subscribeOn(jdbcScheduler)
                .then();
    }

    private JobPost toJobPost(JobPostRequest request) {
        JobPost jobPost = new JobPost();
        return overwriteJobPost(request, jobPost);
    }

    private JobPost toJobPost(JobPost jobPost, JobPostRequest request) {
        return overwriteJobPost(request, jobPost);
    }

    private void saveJobPostSkills(JobPost entity, List<JobPostSkillSetDto> skills) {
        List<JobPostSkillSet> jobPostSkills = skills.stream().map(skill -> {
            JobPostSkillSet skillSet = new JobPostSkillSet(skill.level(), skill.skillId(), entity);
            skillSet.setId(new JobPostSkillSetId(skill.skillId(), entity.getId()));
            return skillSet;
        }).toList();

        entity.setSkills(jobPostSkills);

        jobPostSkillSetRepository.saveAll(jobPostSkills);
    }

    private JobPost overwriteJobPost(JobPostRequest request, JobPost jobPost) {
        JobType jobType = new JobType();
        jobType.setId(request.getJobTypeId());

        JobLocation jobLocation = new JobLocation();
        jobLocation.setId(request.getLocationId());

        List<Specialization> specializations = request.getSpecializationIds().stream().map(id -> {
            Specialization specialization = new Specialization();
            specialization.setId(id);
            return specialization;
        }).toList();

        jobPost.setTitle(request.getTitle());
        jobPost.setDescription(request.getDescription());
        jobPost.setActive(request.isActive());
        jobPost.setSalaryFrom(request.getSalaryFrom());
        jobPost.setSalaryUpTo(request.getSalaryUpTo());
        jobPost.setCurrency(request.getCurrency());
        jobPost.setEmail(request.getEmail());
        jobPost.setPhone(request.getPhone());
        jobPost.setType(jobType);
        jobPost.setLocation(jobLocation);
        jobPost.setPostedById(request.getCompanyId());
        jobPost.setSpecializations(specializations);

        return jobPost;
    }

}
