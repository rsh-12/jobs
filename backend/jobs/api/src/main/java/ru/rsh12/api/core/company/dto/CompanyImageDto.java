package ru.rsh12.api.core.company.dto;
/*
 * Date: 03.04.2022
 * Time: 10:05 AM
 * */

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public record CompanyImageDto(Integer id,
                              String image,
                              Integer companyId) {

}
