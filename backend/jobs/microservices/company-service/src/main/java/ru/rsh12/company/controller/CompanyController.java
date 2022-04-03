package ru.rsh12.company.controller;
/*
 * Date: 03.04.2022
 * Time: 10:42 AM
 * */

import java.time.Instant;
import java.time.LocalDate;
import org.springframework.web.bind.annotation.RestController;
import ru.rsh12.api.core.company.api.CompanyApi;
import ru.rsh12.api.core.company.dto.CompanyDto;

@RestController
public class CompanyController implements CompanyApi {

    @Override
    public CompanyDto getCompany(Integer companyId) {

        return new CompanyDto(1,
                "IT Cat",
                "B2B services",
                LocalDate.now().minusYears(5),
                "",
                2,
                Instant.now(),
                Instant.now());
    }

}
