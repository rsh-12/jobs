package ru.rsh12.company.service.mapper;
/*
 * Date: 10.04.2022
 * Time: 11:39 PM
 * */

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.rsh12.api.core.company.dto.CompanyDto;
import ru.rsh12.company.entity.Company;
import ru.rsh12.company.entity.CompanyImage;
import ru.rsh12.util.ServiceUtil;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CompanyMapper {

    private final BusinessStreamMapper mapper;
    private final ServiceUtil serviceUtil;

    public CompanyDto entityToDto(Company entity) {
        List<String> images = entity.getImages().stream()
                .map(CompanyImage::getImage)
                .toList();

        return new CompanyDto(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getEstablishmentDate(),
                entity.getWebsiteUrl(),
                mapper.entityToDto(entity.getBusinessStream()),
                images,
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                serviceUtil.getServiceAddress()
        );
    }

    public Company dtoToEntity(CompanyDto dto) {
        Company entity = new Company();
        entity.setName(dto.name());
        entity.setDescription(dto.description());
        entity.setEstablishmentDate(dto.establishmentDate());
        entity.setWebsiteUrl(dto.websiteUrl());

        return entity;
    }

}
