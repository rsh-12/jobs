package ru.rsh12.company.controller;
/*
 * Date: 03.04.2022
 * Time: 10:42 AM
 * */

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.rsh12.api.core.company.api.CompanyApi;
import ru.rsh12.api.core.company.dto.CompanyDto;
import ru.rsh12.company.service.CompanyService;
import ru.rsh12.company.service.mapper.CompanyMapper;

@RestController
@RequiredArgsConstructor
public class CompanyController implements CompanyApi {

    private final CompanyService service;
    private final CompanyMapper mapper;

    @Override
    public Mono<CompanyDto> getCompany(Integer companyId) {
        return service.findOne(companyId)
                .map(mapper::entityToDto);
    }

    @Override
    public Flux<CompanyDto> getCompanies(int page, int size) {
        page = Math.max(page, 0);
        size = Math.max(size, 1);

        return service.findAll(PageRequest.of(page, size))
                .map(mapper::entityToDto);
    }

}
