package ru.rsh12.job.service.mapper;
/*
 * Date: 18.04.2022
 * Time: 9:50 AM
 * */

import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import ru.rsh12.api.core.job.dto.SpecializationDto;
import ru.rsh12.job.entity.Specialization;
import ru.rsh12.util.Mapper;

@Component
public class SpecializationMapper implements Mapper<Specialization, SpecializationDto> {

    @Override
    public Specialization dtoToEntity(SpecializationDto dto) {
        return new Specialization(dto.name());
    }

    @Override
    public SpecializationDto entityToDto(Specialization entity) {
        return new SpecializationDto(entity.getId(), entity.getName());
    }

    public Set<Specialization> dtoSetToEntitySet(Set<SpecializationDto> dtos) {
        return dtos.stream().map(this::dtoToEntity).collect(Collectors.toSet());
    }

    public Set<SpecializationDto> entitySetToDtoSet(Set<Specialization> entities) {
        return entities.stream().map(this::entityToDto).collect(Collectors.toSet());
    }

}
