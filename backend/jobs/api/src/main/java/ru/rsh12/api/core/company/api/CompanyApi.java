package ru.rsh12.api.core.company.api;
/*
 * Date: 03.04.2022
 * Time: 10:08 AM
 * */

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rsh12.api.core.company.dto.CompanyDto;

@RequestMapping("/companies")
public interface CompanyApi {

    @GetMapping(
            value = "/{companyId}",
            produces = "application/json")
    CompanyDto getCompany(@PathVariable Integer companyId);

}
