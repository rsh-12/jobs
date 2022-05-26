package ru.rsh12.resume.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import ru.rsh12.api.core.resume.request.ResumeRequest;
import ru.rsh12.api.exceptions.NotFoundException;
import ru.rsh12.resume.entity.Country;
import ru.rsh12.resume.entity.EducationDetail;
import ru.rsh12.resume.entity.ExperienceDetail;
import ru.rsh12.resume.entity.Language;
import ru.rsh12.resume.entity.Resume;
import ru.rsh12.resume.entity.ResumeLanguage;
import ru.rsh12.resume.entity.ResumeLanguageId;
import ru.rsh12.resume.entity.ResumeSkillSet;
import ru.rsh12.resume.entity.ResumeSkillSetId;
import ru.rsh12.resume.entity.SkillSet;
import ru.rsh12.resume.entity.SpecializationResume;
import ru.rsh12.resume.entity.SpecializationResumeId;
import ru.rsh12.resume.repository.ResumeLanguageRepository;
import ru.rsh12.resume.repository.ResumeRepository;
import ru.rsh12.resume.repository.ResumeSkillSetRepository;
import ru.rsh12.resume.repository.SkillSetRepository;
import ru.rsh12.resume.repository.SpecializationResumeRepository;
import ru.rsh12.resume.service.ResumeService;

import java.util.List;
import java.util.UUID;

import static java.util.logging.Level.FINE;

@Slf4j
@Service
public class ResumeServiceImpl implements ResumeService {

    private final ResumeRepository repository;
    private final Scheduler jdbcScheduler;

    private final SpecializationResumeRepository specializationResumeRepository;
    private final ResumeSkillSetRepository resumeSkillSetRepository;
    private final ResumeLanguageRepository resumeLanguageRepository;
    private final SkillSetRepository skillSetRepository;

    public ResumeServiceImpl(ResumeRepository repository,
                             @Qualifier("jdbcScheduler") Scheduler jdbcScheduler,
                             SpecializationResumeRepository specializationResumeRepository,
                             ResumeSkillSetRepository resumeSkillSetRepository,
                             ResumeLanguageRepository resumeLanguageRepository,
                             SkillSetRepository skillSetRepository) {
        this.repository = repository;
        this.jdbcScheduler = jdbcScheduler;
        this.specializationResumeRepository = specializationResumeRepository;
        this.resumeSkillSetRepository = resumeSkillSetRepository;
        this.resumeLanguageRepository = resumeLanguageRepository;
        this.skillSetRepository = skillSetRepository;
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

    @Override
    public Mono<Void> createResume(ResumeRequest request) {
        log.debug("createResume: creates new resume");

        return Mono.fromRunnable(() -> {
                    Resume entity = new Resume();
                    Resume savedEntity = saveOrUpdate(request, entity);
                    saveRelatedEntities(request, savedEntity);
                })
                .doOnError(throwable -> log.warn("deleteJobPost failed: {}", throwable.getMessage()))
                .log(log.getName(), FINE)
                .log(Thread.currentThread().getName(), FINE)
                .subscribeOn(jdbcScheduler)
                .then();
    }

    @Override
    public Mono<Void> updateResume(Integer resumeId, ResumeRequest request) {
        log.debug("updateResume: updates the resume by id {}", resumeId);

        return Mono.fromRunnable(() -> {
                    Resume entity = repository.findById(resumeId)
                            .orElseThrow(NotFoundException::new);

                    saveOrUpdate(request, entity);
                    saveRelatedEntities(request, entity);
                })
                .doOnError(throwable -> log.warn("updateJobPost failed: {}", throwable.getMessage()))
                .log(log.getName(), FINE)
                .log(Thread.currentThread().getName(), FINE)
                .subscribeOn(jdbcScheduler)
                .then();
    }

    @Override
    public Mono<Void> deleteResume(Integer resumeId) {
        log.debug("deleteResume: deletes the resume by id {}", resumeId);

        return Mono.fromRunnable(() -> repository.deleteById(resumeId))
                .doOnError(throwable -> log.warn("Delete resume by id {} failed: {}", resumeId, throwable.getMessage()))
                .onErrorResume(throwable -> Mono.empty())
                .log(log.getName(), FINE)
                .log(Thread.currentThread().getName(), FINE)
                .subscribeOn(jdbcScheduler)
                .then();
    }

    @Override
    public Flux<SkillSet> findSkillsByIdsIn(List<Integer> skillIds) {
        log.debug("findSkillsByIdsIn: finds skills by ids: {}", skillIds);

        return Mono.fromCallable(() -> skillSetRepository.findByIdIn(skillIds))
                .log(log.getName(), FINE)
                .log(Thread.currentThread().getName(), FINE)
                .flatMapMany(Flux::fromIterable)
                .subscribeOn(jdbcScheduler);
    }

    private Resume saveOrUpdate(ResumeRequest request, Resume entity) {
        requestToEntity(request, entity);
        entity.setExperienceDetails(toExperienceDetails(request, entity));
        entity.setEducationDetails(toEducationDetails(request, entity));

        return repository.save(entity);
    }

    private void requestToEntity(ResumeRequest request, Resume entity) {
        // todo get accountId from Security Context
        entity.setAccountId(UUID.randomUUID().toString());

        entity.setDesiredJobPosition(request.getDesiredJobPosition());
        entity.setDescription(request.getDescription());
        entity.setSalary(request.getSalary());
        entity.setCurrency(request.getCurrency());

        List<Country> countries = request.getCountryIds().stream()
                .map(countryId -> {
                    Country country = new Country();
                    country.setId(countryId);
                    return country;
                }).toList();

        entity.setCitizenship(countries);
    }

    private List<ResumeSkillSet> toResumeSkillSet(ResumeRequest request, Resume savedEntity) {
        return request.getSkills().stream()
                .map(skill -> {
                    SkillSet skillSet = new SkillSet(skill.id());

                    ResumeSkillSet resumeSkillSet = new ResumeSkillSet(skill.level(), skillSet, savedEntity);
                    resumeSkillSet.setId(new ResumeSkillSetId(skill.id(), savedEntity.getId()));

                    return resumeSkillSet;
                }).toList();
    }

    private List<ResumeLanguage> toResumeLanguage(ResumeRequest request, Resume resume) {
        return request.getLanguages().stream()
                .map(languageRequest -> {
                    Language language = new Language();
                    language.setId(languageRequest.getId());

                    ResumeLanguage resumeLanguage = new ResumeLanguage(languageRequest.getLevel(), resume, language);
                    resumeLanguage.setId(new ResumeLanguageId(resume.getId(), languageRequest.getId()));

                    return resumeLanguage;
                }).toList();
    }

    private List<ExperienceDetail> toExperienceDetails(ResumeRequest request, Resume resume) {
        return request.getExperience().stream()
                .map(experienceRequest -> new ExperienceDetail(
                        experienceRequest.isCurrentJob(),
                        experienceRequest.getStartDate(),
                        experienceRequest.getEndDate(),
                        experienceRequest.getJobTitle(),
                        experienceRequest.getDescription(),
                        experienceRequest.getCompanyName(),
                        resume)).toList();
    }

    private List<EducationDetail> toEducationDetails(ResumeRequest request, Resume resume) {
        return request.getEducation().stream()
                .map(educationRequest -> new EducationDetail(
                        educationRequest.getInstitutionName(),
                        educationRequest.getFaculty(),
                        educationRequest.getStartingDate(),
                        educationRequest.getCompletionDate(),
                        resume
                )).toList();
    }

    private List<SpecializationResume> toSpecializationResume(ResumeRequest request, Resume entity) {
        return request.getSpecializationIds().stream()
                .map(specializationId -> {
                    SpecializationResume specializationResume = new SpecializationResume(specializationId, entity);
                    specializationResume.setId(new SpecializationResumeId(specializationId, entity.getId()));
                    return specializationResume;
                })
                .toList();
    }

    private void saveRelatedEntities(ResumeRequest request, Resume entity) {
        List<SpecializationResume> specializationResumes = toSpecializationResume(request, entity);
        entity.setSpecializations(specializationResumes);
        specializationResumeRepository.saveAll(specializationResumes);

        List<ResumeLanguage> languages = toResumeLanguage(request, entity);
        entity.setLanguages(languages);
        resumeLanguageRepository.saveAll(languages);

        List<ResumeSkillSet> skills = toResumeSkillSet(request, entity);
        entity.setSkills(skills);
        resumeSkillSetRepository.saveAll(skills);
    }

}
