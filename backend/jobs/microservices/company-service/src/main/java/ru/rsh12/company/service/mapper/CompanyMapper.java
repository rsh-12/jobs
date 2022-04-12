package ru.rsh12.company.service.mapper;
/*
 * Date: 10.04.2022
 * Time: 11:39 PM
 * */

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.rsh12.api.core.company.dto.CompanyDto;
import ru.rsh12.company.entity.Company;
import ru.rsh12.company.entity.CompanyImage;

@Component
@RequiredArgsConstructor
public class CompanyMapper {

    private final BusinessStreamMapper mapper;

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
