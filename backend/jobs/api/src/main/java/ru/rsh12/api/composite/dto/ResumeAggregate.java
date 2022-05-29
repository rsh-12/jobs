package ru.rsh12.api.composite.dto;

import ru.rsh12.api.core.job.dto.SpecializationDto;
import ru.rsh12.api.core.resume.dto.CountryDto;
import ru.rsh12.api.core.resume.dto.EducationDetailDto;
import ru.rsh12.api.core.resume.dto.ExperienceDetailDto;
import ru.rsh12.api.core.resume.dto.ResumeLanguageDto;
import ru.rsh12.api.core.resume.dto.ResumeSkillSetDto;

import java.time.Instant;
import java.util.List;

public record ResumeAggregate(
        Integer id,
        String description,
        Integer salary,
        String currency,
        String desiredJobPosition,
        String accountId,
        Instant createdAt,
        Instant updatedAt,
        List<CountryDto> citizenship,
        List<ResumeSkillSetDto> skills,
        List<ResumeLanguageDto> languages,
        List<EducationDetailDto> education,
        List<ExperienceDetailDto> experience,
        List<SpecializationDto> specializations,
        String serviceAddress
) {
}
