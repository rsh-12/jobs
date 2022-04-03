package ru.rsh12.api.core.company.dto;
/*
 * Date: 03.04.2022
 * Time: 9:59 AM
 * */

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public record BusinessStreamDto(Integer id, String name) {

}
