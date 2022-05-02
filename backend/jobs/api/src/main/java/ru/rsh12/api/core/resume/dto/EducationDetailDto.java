package ru.rsh12.api.core.resume.dto;

import java.time.LocalDate;

public record EducationDetailDto(
        Integer id,
        String institutionName,
        String faculty,
        LocalDate startingDate,
        LocalDate completionDate
) {
}
