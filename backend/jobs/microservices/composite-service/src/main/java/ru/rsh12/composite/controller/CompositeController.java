package ru.rsh12.composite.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.rsh12.api.composite.api.CompositeApi;
import ru.rsh12.api.composite.dto.CompanyJobPosts;
import ru.rsh12.api.composite.dto.JobPostAggregate;
import ru.rsh12.api.composite.dto.ResumeAggregate;
import ru.rsh12.api.core.company.dto.BusinessStreamDto;
import ru.rsh12.api.core.company.dto.CompanyDto;
import ru.rsh12.api.core.company.request.CompanyRequest;
import ru.rsh12.api.core.company.request.BusinessStreamRequest;
import ru.rsh12.api.core.job.dto.JobPostDto;
import ru.rsh12.api.core.job.dto.JobPostSkillSetDto;
import ru.rsh12.api.core.job.dto.SpecializationDto;
import ru.rsh12.api.core.job.request.JobPostRequest;
import ru.rsh12.api.core.resume.dto.ResumeDto;
import ru.rsh12.api.core.resume.dto.SkillSetDto;
import ru.rsh12.api.core.resume.request.ResumeRequest;
import ru.rsh12.api.exceptions.NotFoundException;

import java.util.List;
import java.util.logging.Level;

@RestController
public class CompositeController implements CompositeApi {

    private static final Logger log = LoggerFactory.getLogger(CompositeController.class);

    private final CompositeIntegration integration;

    @Autowired
    public CompositeController(CompositeIntegration integration) {
        this.integration = integration;
    }

    @Override
    public Mono<CompanyDto> getCompany(Integer companyId) {
        return integration.getCompany(companyId)
                .doOnError(ex -> log.warn("Get company by ID='{}' failed: {}", companyId, ex.toString()));
    }

    @Override
    public Flux<CompanyDto> getCompanies(int page, int size) {
        return integration.getCompanies(page, size)
                .doOnError(ex -> log.warn("Get companies failed: {}", ex.toString()));
    }

    @Override
    public Mono<CompanyJobPosts> getCompanyJobPosts(Integer companyId, int page, int size) {
        return Mono.zip(values -> createCompanyAggregate((CompanyDto) values[0], (List<JobPostDto>) values[1]),
                        integration.getCompany(companyId),
                        integration.getCompanyJobPosts(companyId, page, size).collectList())
                .doOnError(ex -> log.warn("Get company(ID='{}') vacancies failed: {}", companyId, ex.toString()))
                .log(log.getName(), Level.FINE);
    }

    @Override
    public Mono<JobPostAggregate> getJobPost(Integer jobPostId) {
        return integration.getJobPost(jobPostId)
                .flatMap(jobPostDto -> {
                    List<Integer> skillIds = jobPostDto.skills().stream()
                            .map(JobPostSkillSetDto::skillId)
                            .toList();

                    return integration.getSkillsByIdsIn(skillIds).collectList()
                            .map(skills -> toJobPostAggregate(jobPostDto, skills));
                })
                .doOnError(ex -> log.warn("Get job post by ID='{}' failed: {}", jobPostId, ex.toString()));
    }

    @Override
    public Flux<JobPostAggregate> getJobPosts(int page, int size) {
        return integration.getJobPosts(page, size)
                .flatMap(jobPostDto -> {
                    List<Integer> skillIds = jobPostDto.skills().stream()
                            .map(JobPostSkillSetDto::skillId)
                            .toList();

                    return integration.getSkillsByIdsIn(skillIds).collectList()
                            .mapNotNull(skills -> toJobPostAggregate(jobPostDto, skills));
                })
                .doOnError(ex -> log.warn("Get job posts failed: {}", ex.toString()));
    }

    @Override
    public Mono<Void> createJobPost(Integer companyId, JobPostRequest request) {
        return integration.companyExistsById(companyId)
                .flatMap(companyExists -> {
                    if (!companyExists) {
                        return Mono.error(new NotFoundException("No company with id '" + companyId + "' found!"));
                    }
                    return integration.createJobPost(companyId, request);
                })
                .doOnError(ex -> log.error("Create job post failed: {}", ex.toString()))
                .onErrorMap(throwable -> new NotFoundException(throwable.getMessage()));
    }

    @Override
    public Mono<Void> updateJobPost(Integer companyId, Integer jobPostId, JobPostRequest request) {
        return integration.companyExistsById(companyId)
                .flatMap(companyExists -> {
                    if (!companyExists) {
                        return Mono.error(new NotFoundException("No company with id '" + companyId + "' found!"));
                    }
                    return integration.updateJobPost(companyId, jobPostId, request);
                })
                .doOnError(ex -> log.error("Update job post by ID='{}' failed: {}", jobPostId, ex.toString()))
                .onErrorMap(throwable -> new NotFoundException(throwable.getMessage()));
    }

    @Override
    public Mono<Void> deleteJobPost(Integer companyId, Integer jobPostId) {
        return integration.companyExistsById(companyId)
                .flatMap(companyExists -> {
                    if (!companyExists) {
                        return Mono.error(new NotFoundException("No company with id '" + companyId + "' found!"));
                    }
                    return integration.deleteJobPost(jobPostId);
                })
                .doOnError(ex -> log.error("Delete job post by ID='{}' failed: {}", jobPostId, ex.toString()))
                .onErrorMap(throwable -> new NotFoundException(throwable.getMessage()));
    }

    @Override
    public Mono<ResumeAggregate> getResume(Integer resumeId) {
        return integration.getResume(resumeId)
                .flatMap(resume -> integration
                        .getSpecializationsById(resume.specializationIds().stream().toList())
                        .collectList()
                        .mapNotNull(specializations -> toResumeAggregate(resume, specializations)))
                .doOnError(ex -> log.warn("Get aggregate resume by ID='{}' failed: {}", resumeId, ex.toString()));
    }

    @Override
    public Flux<ResumeDto> getResumes(int page, int size) {
        return integration.getResumes(page, size)
                .doOnError(ex -> log.warn("Get resumes failed: {}", ex.toString()));
    }

    @Override
    public Mono<Void> createResume(ResumeRequest request) {
        return integration.createResume(request)
                .doOnError(ex -> log.warn("Create resume failed: {}", ex.toString()));
    }

    @Override
    public Mono<Void> updateResume(Integer resumeId, ResumeRequest request) {
        return integration.updateResume(resumeId, request)
                .doOnError(ex -> log.warn("Update resume by ID='{}' failed: {}", resumeId, ex.toString()));
    }

    @Override
    public Mono<Void> deleteResume(Integer resumeId) {
        return integration.deleteResume(resumeId)
                .doOnError(ex -> log.warn("Create resume failed: {}", ex.toString()));
    }

    @Override
    public Mono<BusinessStreamDto> getBusinessStream(Integer industryId) {
        return integration.getBusinessStream(industryId)
                .doOnError(ex -> log.warn("Get industry by ID='{}' failed: {}", industryId, ex.toString()));
    }

    @Override
    public Flux<BusinessStreamDto> getBusinessStreams(int page, int size) {
        return integration.getBusinessStreams(page, size)
                .doOnError(ex -> log.warn("Get industries failed: {}", ex.toString()));
    }

    @Override
    public Mono<BusinessStreamDto> createBusinessStream(BusinessStreamRequest request) {
        return integration.createBusinessStream(request)
                .doOnError(ex -> log.warn("Create industry failed: {}", ex.toString()));
    }

    @Override
    public Mono<CompanyDto> createCompany(Integer industryId, CompanyRequest request) {
        return integration.createCompany(industryId, request)
                .doOnError(ex -> log.warn("Create company failed: {}", ex.toString()));
    }

    @Override
    public Mono<Void> updateCompany(Integer industryId, Integer companyId, CompanyRequest request) {
        return integration.updateCompany(industryId, companyId, request)
                .doOnError(ex -> log.warn("Update company by ID='{}' failed: {}", companyId, ex.getMessage()))
                .then();
    }

    @Override
    public Mono<Void> deleteCompany(Integer industryId, Integer companyId) {
        return integration.deleteCompany(companyId)
                .doOnError(ex -> log.warn("Delete company by ID='{}' failed: {}", companyId, ex.getMessage()));
    }

    private CompanyJobPosts createCompanyAggregate(CompanyDto companyDto, List<JobPostDto> jobPostDtos) {
        String companyServiceAddress = companyDto.serviceAddress();
        String jobPostServiceAddress = jobPostDtos.stream().findFirst().map(JobPostDto::serviceAddress).orElse("");

        return new CompanyJobPosts(companyDto, jobPostDtos, companyServiceAddress, jobPostServiceAddress);
    }

    private JobPostAggregate toJobPostAggregate(JobPostDto jobPostDto, List<SkillSetDto> skills) {
        return new JobPostAggregate(
                jobPostDto.id(),
                jobPostDto.title(),
                jobPostDto.description(),
                jobPostDto.isActive(),
                jobPostDto.salaryFrom(),
                jobPostDto.salaryUpTo(),
                jobPostDto.currency(),
                jobPostDto.email(),
                jobPostDto.email(),
                jobPostDto.type(),
                jobPostDto.location(),
                jobPostDto.postedById(),
                jobPostDto.specializations(),
                skills,
                jobPostDto.createdAt(),
                jobPostDto.updatedAt(),
                jobPostDto.serviceAddress()
        );
    }

    private ResumeAggregate toResumeAggregate(ResumeDto resume, List<SpecializationDto> specializations) {
        return new ResumeAggregate(
                resume.id(),
                resume.description(),
                resume.salary(),
                resume.currency(),
                resume.desiredJobPosition(),
                resume.accountId(),
                resume.createdAt(),
                resume.updatedAt(),
                resume.citizenship(),
                resume.skills(),
                resume.languages(),
                resume.education(),
                resume.experience(),
                specializations,
                resume.serviceAddress()
        );
    }

}
