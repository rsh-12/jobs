package ru.rsh12.api.core.company.dto;
/*
 * Date: 03.04.2022
 * Time: 10:05 AM
 * */

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

// todo: add some validation
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public record CompanyDto(
        Integer id,
        String name,
        String description,
        LocalDate establishmentDate,
        String websiteUrl,
        BusinessStreamDto businessStreamDto,
        List<String> images,
        Instant createdAt,
        Instant updatedAt,
        String serviceAddress) {

}
