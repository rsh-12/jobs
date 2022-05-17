package ru.rsh12.api.core.company.api;
/*
 * Date: 03.04.2022
 * Time: 10:08 AM
 * */

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.rsh12.api.core.company.dto.CompanyDto;
import ru.rsh12.api.core.company.request.CompanyRequest;

import javax.validation.Valid;

public interface CompanyApi {

    @GetMapping(value = "/api/v1/companies/{companyId}", produces = "application/json")
    Mono<CompanyDto> getCompany(@PathVariable Integer companyId);

    @GetMapping(value = "/api/v1/companies", produces = "application/json")
    Flux<CompanyDto> getCompanies(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size);

    @PostMapping(
            value = "/api/v1/industries/{industryId}/companies",
            consumes = "application/json",
            produces = "application/json")
    Mono<CompanyDto> createCompany(
            @PathVariable("industryId") Integer industryId,
            @Valid @RequestBody CompanyRequest request);

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/api/v1/companies/{companyId}")
    Mono<Void> deleteCompany(@PathVariable("companyId") Integer companyId);

    @PutMapping(value = "/api/v1/industries/{industryId}/companies/{companyId}")
    Mono<CompanyDto> updateCompany(
            @PathVariable("industryId") Integer industryId,
            @PathVariable("companyId") Integer companyId,
            @Valid @RequestBody CompanyRequest request);

    @GetMapping(value = "/api/v1/companies/{companyId}/exists")
    Mono<Boolean> companyExistsById(@PathVariable("companyId") Integer companyId);

}
