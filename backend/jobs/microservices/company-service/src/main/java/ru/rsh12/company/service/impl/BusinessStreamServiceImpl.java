package ru.rsh12.company.service.impl;
/*
 * Date: 09.04.2022
 * Time: 10:59 PM
 * */

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import ru.rsh12.api.core.company.request.BusinessStreamRequest;
import ru.rsh12.company.entity.BusinessStream;
import ru.rsh12.company.entity.Company;
import ru.rsh12.company.entity.CompanyImage;
import ru.rsh12.company.repository.BusinessStreamRepository;
import ru.rsh12.company.service.BusinessStreamService;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.logging.Level.FINE;

@Slf4j
@Service
public class BusinessStreamServiceImpl implements BusinessStreamService {

    private final BusinessStreamRepository repository;
    private final Scheduler jdbcScheduler;

    @Autowired
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
                .log(log.getName(), FINE)
                .log(Thread.currentThread().getName(), FINE)
                .flatMapMany(Flux::fromIterable)
                .subscribeOn(jdbcScheduler);
    }

    @Override
    public Mono<BusinessStream> createBusinessStream(BusinessStreamRequest request) {
        log.debug("createBusinessStream: creates a business stream");

        BusinessStream businessStream = new BusinessStream(request.getName());
        if (!request.getCompanies().isEmpty()) {
            List<Company> companies = toCompaniesWithImages(request, businessStream);
            businessStream.setCompanies(companies);
        }

        return Mono.fromCallable(() -> repository.save(businessStream))
                .log(log.getName(), FINE)
                .log(Thread.currentThread().getName(), FINE)
                .subscribeOn(jdbcScheduler);
    }

    private List<Company> toCompaniesWithImages(BusinessStreamRequest request, BusinessStream businessStream) {
        return request.getCompanies().stream().map(createCompanyRequest -> {
            List<CompanyImage> images = createCompanyRequest.getImages().stream()
                    .map(CompanyImage::new)
                    .toList();

            Company company = new Company(
                    createCompanyRequest.getName(),
                    createCompanyRequest.getDescription(),
                    createCompanyRequest.getEstablishmentDate(),
                    createCompanyRequest.getWebsiteUrl(),
                    businessStream,
                    images);

            images.forEach(img -> img.setCompany(company));

            return company;
        }).collect(Collectors.toList());
    }

}
