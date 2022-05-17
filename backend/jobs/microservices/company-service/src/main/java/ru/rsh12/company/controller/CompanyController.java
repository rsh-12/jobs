package ru.rsh12.company.controller;
/*
 * Date: 03.04.2022
 * Time: 10:42 AM
 * */

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.rsh12.api.core.company.api.CompanyApi;
import ru.rsh12.api.core.company.dto.CompanyDto;
import ru.rsh12.api.core.company.request.CompanyRequest;
import ru.rsh12.api.exceptions.InvalidInputException;
import ru.rsh12.api.exceptions.NotFoundException;
import ru.rsh12.company.service.CompanyService;
import ru.rsh12.company.service.mapper.CompanyMapper;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CompanyController implements CompanyApi {

    private final CompanyService service;
    private final CompanyMapper mapper;

    @Override
    public Mono<CompanyDto> getCompany(Integer companyId) {
        return service.findOne(companyId)
                .map(mapper::entityToDto)
                .switchIfEmpty(Mono.error(() -> new NotFoundException("Entity not found")));
    }

    @Override
    public Flux<CompanyDto> getCompanies(int page, int size) {
        page = Math.max(page, 0);
        size = Math.max(size, 1);

        return service.findAll(PageRequest.of(page, size))
                .map(mapper::entityToDto);
    }

    @Override
    public Mono<CompanyDto> createCompany(Integer industryId, CompanyRequest request) {
        return service.createCompany(industryId, request)
                .map(mapper::entityToDto)
                .onErrorMap(ex -> new InvalidInputException(ex.getMessage()));
    }

    @Override
    public Mono<Void> deleteCompany(Integer companyId) {
        return service.deleteCompany(companyId)
                .doOnError(ex -> log.warn("delete company by id failed: {}", ex.toString()))
                .onErrorResume(throwable -> Mono.empty());
    }

    @Override
    public Mono<CompanyDto> updateCompany(Integer industryId, Integer companyId, CompanyRequest request) {
        return service.updateCompany(industryId, companyId, request)
                .map(mapper::entityToDto)
                .onErrorMap(ex -> new InvalidInputException(ex.getMessage()));
    }

    @Override
    public Mono<Boolean> companyExistsById(Integer companyId) {
        return service.existsById(companyId);
    }

}
