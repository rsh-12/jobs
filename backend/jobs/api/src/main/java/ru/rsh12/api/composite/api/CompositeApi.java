package ru.rsh12.api.composite.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Mono;
import ru.rsh12.api.core.company.dto.CompanyDto;

public interface CompositeApi {

    @GetMapping("/api/v1/composite/companies/{companyId}")
    Mono<CompanyDto> getCompany(@PathVariable("companyId") Integer companyId);

}
