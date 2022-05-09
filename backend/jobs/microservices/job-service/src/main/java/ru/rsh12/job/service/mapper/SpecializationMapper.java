package ru.rsh12.job.service.mapper;
/*
 * Date: 18.04.2022
 * Time: 9:50 AM
 * */

import org.springframework.stereotype.Component;
import ru.rsh12.api.core.job.dto.SpecializationDto;
import ru.rsh12.job.entity.Specialization;
import ru.rsh12.util.mapper.CommonListMapper;

import java.util.List;

@Component
public class SpecializationMapper implements CommonListMapper<Specialization, SpecializationDto> {

    @Override
    public Specialization dtoToEntity(SpecializationDto dto) {
        return new Specialization(dto.name());
    }

    @Override
    public SpecializationDto entityToDto(Specialization entity) {
        return new SpecializationDto(entity.getId(), entity.getName());
    }

    @Override
    public List<Specialization> dtoListToEntityList(List<SpecializationDto> dtoList) {
        return dtoList.stream().map(this::dtoToEntity).toList();
    }

    @Override
    public List<SpecializationDto> entityListToDtoList(List<Specialization> entityList) {
        return entityList.stream().map(this::entityToDto).toList();
    }
}
