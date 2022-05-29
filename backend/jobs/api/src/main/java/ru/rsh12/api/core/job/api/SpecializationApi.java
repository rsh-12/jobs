package ru.rsh12.api.core.job.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;
import ru.rsh12.api.core.job.dto.SpecializationDto;

import java.util.List;

public interface SpecializationApi {

    @GetMapping(value = "/api/v1/specializations", produces = "application/json")
    Flux<SpecializationDto> getSpecializationsById(@RequestParam("ids") List<Integer> specializationIds);

}
