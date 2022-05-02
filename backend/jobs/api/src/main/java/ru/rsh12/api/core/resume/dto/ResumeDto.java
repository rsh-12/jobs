package ru.rsh12.api.core.resume.dto;
/*
 * Date: 03.04.2022
 * Time: 11:30 AM
 * */

import java.time.Instant;
import java.util.Set;

public record ResumeDto(
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
        Set<Integer> specializationIds
) {

}