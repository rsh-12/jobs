package ru.rsh12.api.core.resume.dto;

import java.time.LocalDate;

public record ExperienceDetailDto(
        Integer id,
        boolean isCurrentJob,
        LocalDate startDate,
        LocalDate endDate,
        String jobTitle,
        String description,
        String companyName
) {
}
