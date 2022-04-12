package ru.rsh12.company.service;

import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.rsh12.company.entity.Company;

public interface CompanyService {

    Mono<Company> findOne(Integer companyId);

    Flux<Company> findAll(Pageable pageable);

}
