package ru.rsh12.api.composite.dto;

import ru.rsh12.api.core.job.dto.SpecializationDto;
import ru.rsh12.api.core.resume.dto.CountryDto;
import ru.rsh12.api.core.resume.dto.EducationDetailDto;
import ru.rsh12.api.core.resume.dto.ExperienceDetailDto;
import ru.rsh12.api.core.resume.dto.LanguageDto;
import ru.rsh12.api.core.resume.dto.SkillSetDto;

import java.time.Instant;
import java.util.Set;

public record ResumeAggregate(
        Integer id,
        String description,
        Integer salary,
        String currency,
        String desiredJobPosition,
        String accountId,
        Instant createdAt,
        Instant updatedAt,
        Set<CountryDto> citizenship,
        Set<SkillSetDto> skills,
        Set<LanguageDto> languages,
        Set<EducationDetailDto> education,
        Set<ExperienceDetailDto> experience,
        Set<SpecializationDto> specializations,
        String serviceAddress
) {
}
