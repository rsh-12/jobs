package ru.rsh12.api.core.company.api;
/*
 * Date: 03.04.2022
 * Time: 10:08 AM
 * */

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.rsh12.api.core.company.dto.CompanyDto;

public interface CompanyApi {

    @GetMapping(value = "/api/v1/companies/{companyId}", produces = "application/json")
    Mono<CompanyDto> getCompany(@PathVariable Integer companyId);

    @GetMapping(value = "/api/v1/companies", produces = "application/json")
    Flux<CompanyDto> getCompanies(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size);

}
