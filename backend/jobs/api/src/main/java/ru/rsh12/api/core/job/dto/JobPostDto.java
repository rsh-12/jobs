package ru.rsh12.api.core.job.dto;
/*
 * Date: 03.04.2022
 * Time: 11:20 AM
 * */

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import java.time.Instant;
import java.util.Set;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public record JobPostDto(
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
        Set<SpecializationDto> specializations,
        Set<JobPostSkillSetDto> skills,
        Instant createdAt,
        Instant updatedAt,
        String serviceAddress) {

}