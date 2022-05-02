package ru.rsh12.resume.service.mapper;

import org.springframework.stereotype.Component;
import ru.rsh12.api.core.resume.dto.LanguageDto;
import ru.rsh12.resume.entity.Language;
import ru.rsh12.util.CommonSetMapper;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class LanguageMapper implements CommonSetMapper<Language, LanguageDto> {

    @Override
    public Language dtoToEntity(LanguageDto dto) {
        return new Language(dto.name());
    }

    @Override
    public LanguageDto entityToDto(Language entity) {
        return new LanguageDto(entity.getId(), entity.getName());
    }

    @Override
    public Set<Language> dtoSetToEntitySet(Set<LanguageDto> dtoSet) {
        return dtoSet.stream().map(this::dtoToEntity).collect(Collectors.toSet());
    }

    @Override
    public Set<LanguageDto> entitySetToDtoSet(Set<Language> entitySet) {
        return entitySet.stream().map(this::entityToDto).collect(Collectors.toSet());
    }
}
