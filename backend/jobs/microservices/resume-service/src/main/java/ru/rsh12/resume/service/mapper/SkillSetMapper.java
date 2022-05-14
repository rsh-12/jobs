package ru.rsh12.resume.service.mapper;

import org.springframework.stereotype.Component;
import ru.rsh12.api.core.resume.dto.SkillSetDto;
import ru.rsh12.resume.entity.SkillSet;
import ru.rsh12.util.mapper.Mapper;

@Component
public class SkillSetMapper implements Mapper<SkillSet, SkillSetDto> {

    @Override
    public SkillSet dtoToEntity(SkillSetDto dto) {
        return new SkillSet(dto.id(), dto.name());
    }

    @Override
    public SkillSetDto entityToDto(SkillSet entity) {
        return new SkillSetDto(entity.getId(), entity.getName());
    }

}
