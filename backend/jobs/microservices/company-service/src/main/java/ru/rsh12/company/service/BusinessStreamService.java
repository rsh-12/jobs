package ru.rsh12.company.service;

import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.rsh12.company.entity.BusinessStream;

public interface BusinessStreamService {

    Mono<BusinessStream> findOne(Integer id);

    Flux<BusinessStream> findAll(Pageable pageable);

}
