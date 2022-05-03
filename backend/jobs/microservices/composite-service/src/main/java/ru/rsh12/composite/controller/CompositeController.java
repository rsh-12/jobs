package ru.rsh12.composite.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.rsh12.api.composite.api.CompositeApi;
import ru.rsh12.api.core.company.dto.CompanyDto;

@RestController
public class CompositeController implements CompositeApi {

    private static final Logger log = LoggerFactory.getLogger(CompositeController.class);

    private final CompositeIntegration integration;

    @Autowired
    public CompositeController(CompositeIntegration integration) {
        this.integration = integration;
    }

    @Override
    public Mono<CompanyDto> getCompany(Integer companyId) {
        return integration.getCompany(companyId)
                .doOnError(ex -> log.warn("get company by id failed: {}", ex.toString()));
    }

}
