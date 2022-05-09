package ru.rsh12.resume.service.mapper;

import org.springframework.stereotype.Component;
import ru.rsh12.api.core.resume.dto.CountryDto;
import ru.rsh12.resume.entity.Country;
import ru.rsh12.util.mapper.CommonListMapper;

import java.util.List;

@Component
public class CountryMapper implements CommonListMapper<Country, CountryDto> {

    @Override
    public Country dtoToEntity(CountryDto dto) {
        return new Country(dto.name());
    }

    @Override
    public CountryDto entityToDto(Country entity) {
        return new CountryDto(entity.getId(), entity.getName());
    }

    @Override
    public List<Country> dtoListToEntityList(List<CountryDto> dtoList) {
        return dtoList.stream().map(this::dtoToEntity).toList();
    }

    @Override
    public List<CountryDto> entityListToDtoList(List<Country> entityList) {
        return entityList.stream().map(this::entityToDto).toList();
    }
}
