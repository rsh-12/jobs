package ru.rsh12.resume.service.mapper;

import org.springframework.stereotype.Component;
import ru.rsh12.api.core.resume.dto.CountryDto;
import ru.rsh12.resume.entity.Country;
import ru.rsh12.util.mapper.CommonSetMapper;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CountryMapper implements CommonSetMapper<Country, CountryDto> {

    @Override
    public Country dtoToEntity(CountryDto dto) {
        return new Country(dto.name());
    }

    @Override
    public CountryDto entityToDto(Country entity) {
        return new CountryDto(entity.getId(), entity.getName());
    }

    @Override
    public Set<Country> dtoSetToEntitySet(Set<CountryDto> dtoSet) {
        return dtoSet.stream().map(this::dtoToEntity)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<CountryDto> entitySetToDtoSet(Set<Country> entitySet) {
        return entitySet.stream().map(this::entityToDto)
                .collect(Collectors.toSet());
    }
}
