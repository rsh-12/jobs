package ru.rsh12.api.core.job.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public record JobLocationDto(
        Integer id,
        String street,
        String city,
        String state,
        String country) {

}