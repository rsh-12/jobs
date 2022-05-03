package ru.rsh12.company.service.mapper;
/*
 * Date: 10.04.2022
 * Time: 11:09 AM
 * */

import org.springframework.stereotype.Component;
import ru.rsh12.api.core.company.dto.BusinessStreamDto;
import ru.rsh12.company.entity.BusinessStream;
import ru.rsh12.util.mapper.Mapper;

@Component
public class BusinessStreamMapper implements Mapper<BusinessStream, BusinessStreamDto> {

    @Override
    public BusinessStreamDto entityToDto(BusinessStream entity) {
        return new BusinessStreamDto(entity.getId(), entity.getName());
    }

    @Override
    public BusinessStream dtoToEntity(BusinessStreamDto dto) {
        return new BusinessStream(dto.name());
    }

}
