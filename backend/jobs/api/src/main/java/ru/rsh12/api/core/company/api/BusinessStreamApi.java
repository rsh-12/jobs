package ru.rsh12.api.core.company.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.rsh12.api.core.company.dto.BusinessStreamDto;
import ru.rsh12.api.core.company.request.BusinessStreamRequest;

import javax.validation.Valid;

@RequestMapping("/api/v1/industries")
public interface BusinessStreamApi {

    @GetMapping(value = "/{id}", produces = "application/json")
    Mono<BusinessStreamDto> getBusinessStream(@PathVariable Integer id);

    @GetMapping(produces = "application/json")
    Flux<BusinessStreamDto> getBusinessStreams(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size);

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json", produces = "application/json")
    Mono<BusinessStreamDto> createBusinessStream(@Valid @RequestBody BusinessStreamRequest request);

}
