package ru.rsh12.company.service.impl;
/*
 * Date: 11.04.2022
 * Time: 8:33 AM
 * */

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import ru.rsh12.api.core.company.request.CompanyRequest;
import ru.rsh12.api.exceptions.NotFoundException;
import ru.rsh12.company.entity.BusinessStream;
import ru.rsh12.company.entity.Company;
import ru.rsh12.company.entity.CompanyImage;
import ru.rsh12.company.repository.CompanyRepository;
import ru.rsh12.company.service.BusinessStreamService;
import ru.rsh12.company.service.CompanyService;

import java.util.List;

import static java.util.logging.Level.FINE;

@Slf4j
@Service
public class CompanyServiceImpl implements CompanyService {

    private final BusinessStreamService businessStreamService;
    private final CompanyRepository repository;
    private final Scheduler jdbcScheduler;

    @Autowired
    public CompanyServiceImpl(
            BusinessStreamService businessStreamService,
            CompanyRepository repository,
            @Qualifier("jdbcScheduler") Scheduler jdbcScheduler) {
        this.businessStreamService = businessStreamService;
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

    @Override
    public Mono<Company> createCompany(Integer industryId, CompanyRequest request) {
        log.debug("createCompany: creates new company");

        return businessStreamService.findOne(industryId)
                .switchIfEmpty(Mono.error(() -> new NotFoundException("Business stream not found")))
                .map(businessStream -> repository.save(createCompany(request, businessStream)))
                .log(log.getName(), FINE)
                .log(Thread.currentThread().getName(), FINE)
                .flatMap(Mono::justOrEmpty)
                .subscribeOn(jdbcScheduler);
    }

    @Override
    public Mono<Company> updateCompany(Integer industryId, Integer companyId, CompanyRequest request) {
        log.debug("updateCompany: updates company with id {}", companyId);

        return businessStreamService.findOne(industryId)
                .switchIfEmpty(Mono.error(() -> new NotFoundException("Business stream not found")))
                .map(businessStream -> repository.findById(companyId)
                        .map(company -> {
                            Company updatedEntity = requestToEntity(company, request);
                            updatedEntity.setBusinessStream(businessStream);
                            return repository.save(updatedEntity);
                        }))
                .log(log.getName(), FINE)
                .log(Thread.currentThread().getName(), FINE)
                .flatMap(Mono::justOrEmpty)
                .subscribeOn(jdbcScheduler);
    }

    @Override
    public Mono<Boolean> existsById(Integer companyId) {
        log.info("existsById: checks if a company exists by ID");

        return Mono.fromCallable(() -> repository.existsById(companyId))
                .log(log.getName(), FINE)
                .log(Thread.currentThread().getName(), FINE)
                .flatMap(Mono::justOrEmpty)
                .subscribeOn(jdbcScheduler);
    }

    @Override
    public Mono<Void> deleteCompany(Integer companyId) {
        log.debug("deleteCompany: deletes company by id");

        return Mono.fromRunnable(() -> repository.deleteById(companyId))
                .log(log.getName(), FINE)
                .log(Thread.currentThread().getName(), FINE)
                .subscribeOn(jdbcScheduler)
                .then();
    }

    private Company createCompany(CompanyRequest request, BusinessStream businessStream) {
        List<CompanyImage> images = stringToCompanyImages(request.getImages());

        Company company = new Company(
                request.getName(),
                request.getDescription(),
                request.getEstablishmentDate(),
                request.getWebsiteUrl(),
                businessStream,
                images);

        images.forEach(image -> image.setCompany(company));

        return company;
    }

    private Company requestToEntity(Company entity, CompanyRequest request) {
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setEstablishmentDate(request.getEstablishmentDate());
        entity.setWebsiteUrl(request.getWebsiteUrl());

        if (request.getImages() != null) {
            entity.setImages(stringToCompanyImages(request.getImages()));
        }

        return entity;
    }

    private List<CompanyImage> stringToCompanyImages(List<String> images) {
        return images.stream().map(CompanyImage::new).toList();
    }

}
