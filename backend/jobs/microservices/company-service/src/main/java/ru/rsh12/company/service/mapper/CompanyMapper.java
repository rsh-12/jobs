package ru.rsh12.company.service.mapper;
/*
 * Date: 10.04.2022
 * Time: 11:39 PM
 * */

import org.springframework.stereotype.Component;
import ru.rsh12.api.core.company.dto.CompanyDto;
import ru.rsh12.company.entity.Company;

@Component
public class CompanyMapper {

    public CompanyDto entityToDto(Company entity) {
        return new CompanyDto(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getEstablishmentDate(),
                entity.getWebsiteUrl(),
                entity.getBusinessStream().getId(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public Company dtoToEntity(CompanyDto dto) {
        return Company.builder()
                .name(dto.name())
                .description(dto.description())
                .establishmentDate(dto.establishmentDate())
                .websiteUrl(dto.websiteUrl())
                .build();
    }

}
