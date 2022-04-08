package ru.rsh12.api.core.company.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rsh12.api.core.company.dto.BusinessStreamDto;

@RequestMapping("/api/v1/industries")
public interface BusinessStreamApi {

    @GetMapping(value = "/{businessStreamId}", produces = "application/json")
    BusinessStreamDto getBusinessStream(@PathVariable Integer businessStreamId);

}
