package ru.rsh12.company.controller;
/*
 * Date: 10.04.2022
 * Time: 10:55 AM
 * */

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.rsh12.api.core.company.api.BusinessStreamApi;
import ru.rsh12.api.core.company.dto.BusinessStreamDto;
import ru.rsh12.api.exceptions.NotFoundException;
import ru.rsh12.company.service.BusinessStreamService;
import ru.rsh12.company.service.mapper.BusinessStreamMapper;

@RestController
@RequiredArgsConstructor
public class BusinessStreamController implements BusinessStreamApi {

    private final BusinessStreamService service;
    private final BusinessStreamMapper mapper;

    @Override
    public Mono<BusinessStreamDto> getBusinessStream(Integer id) {
        return service.findOne(id)
                .map(mapper::entityToDto)
                .switchIfEmpty(Mono.error(new NotFoundException("oh no")));
    }

    @Override
    public Flux<BusinessStreamDto> getBusinessStreams(int page, int size) {
        page = Math.max(page, 0);
        size = Math.max(size, 1);

        return service.findAll(PageRequest.of(page, size))
                .map(mapper::entityToDto);
    }

}
