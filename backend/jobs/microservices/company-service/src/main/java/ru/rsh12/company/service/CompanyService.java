package ru.rsh12.company.service;

import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.rsh12.api.core.company.request.CompanyRequest;
import ru.rsh12.company.entity.Company;

public interface CompanyService {

    Mono<Company> findOne(Integer companyId);

    Flux<Company> findAll(Pageable pageable);

    Mono<Company> createCompany(Integer industryId, CompanyRequest request);

    Mono<Void> deleteCompany(Integer companyId);

    Mono<Company> updateCompany(Integer industryId, Integer companyId, CompanyRequest request);

    Mono<Boolean> existsById(Integer companyId);
}
