package ru.rsh12.job.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.rsh12.api.core.job.api.SpecializationApi;
import ru.rsh12.api.core.job.dto.SpecializationDto;
import ru.rsh12.job.service.SpecializationService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SpecializationController implements SpecializationApi {

    private final SpecializationService service;

    @Override
    public Flux<SpecializationDto> getSpecializationsById(List<Integer> specializationIds) {
        return service.findAllById(specializationIds)
                .map(specialization ->
                        new SpecializationDto(specialization.getId(), specialization.getName()));
    }

}
