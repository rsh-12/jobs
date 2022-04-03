package ru.rsh12.api.core.job.dto;
/*
 * Date: 03.04.2022
 * Time: 11:20 AM
 * */

import java.time.Instant;

public record JobPostDto(Integer id,
                         String title,
                         String description,
                         boolean isActive,
                         Integer salaryFrom,
                         Integer salaryUpTo,
                         String currency,
                         String email,
                         String phone,
                         Integer jobTypeId,
                         Integer jobLocationId,
                         Integer postedById,
                         Instant createdAt,
                         Instant updatedAt) {

}