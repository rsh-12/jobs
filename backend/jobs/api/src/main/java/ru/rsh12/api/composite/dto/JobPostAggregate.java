package ru.rsh12.api.composite.dto;

import ru.rsh12.api.core.job.dto.JobLocationDto;
import ru.rsh12.api.core.job.dto.JobTypeDto;
import ru.rsh12.api.core.job.dto.SpecializationDto;
import ru.rsh12.api.core.resume.dto.SkillSetDto;

import java.time.Instant;
import java.util.List;

public record JobPostAggregate(
        Integer id,
        String title,
        String description,
        boolean isActive,
        Integer salaryFrom,
        Integer salaryUpTo,
        String currency,
        String email,
        String phone,
        JobTypeDto type,
        JobLocationDto location,
        Integer postedById,
        List<SpecializationDto> specializations,
        List<SkillSetDto> skills,
        Instant createdAt,
        Instant updatedAt,
        String serviceAddress
) {
}
