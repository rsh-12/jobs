package ru.rsh12.api.core.resume.dto;
/*
 * Date: 03.04.2022
 * Time: 11:30 AM
 * */

import java.time.Instant;

public record ResumeDto(Integer id,
                        String description,
                        Integer salary,
                        String currency,
                        String desiredJobPosition,
                        String accountId,
                        Instant createdAt,
                        Instant updatedAt) {

}