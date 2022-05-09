package ru.rsh12.resume.service.mapper;

import org.springframework.stereotype.Component;
import ru.rsh12.api.core.resume.dto.ExperienceDetailDto;
import ru.rsh12.resume.entity.ExperienceDetail;
import ru.rsh12.util.mapper.CommonListMapper;

import java.util.List;

@Component
public class ExperienceDetailMapper implements CommonListMapper<ExperienceDetail, ExperienceDetailDto> {

    @Override
    public ExperienceDetail dtoToEntity(ExperienceDetailDto dto) {
        return new ExperienceDetail(
                dto.isCurrentJob(),
                dto.startDate(),
                dto.endDate(),
                dto.jobTitle(),
                dto.description(),
                dto.companyName(),
                null);
    }

    @Override
    public ExperienceDetailDto entityToDto(ExperienceDetail entity) {
        return new ExperienceDetailDto(
                entity.getId(),
                entity.isCurrentJob(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getJobTitle(),
                entity.getDescription(),
                entity.getCompanyName());
    }

    @Override
    public List<ExperienceDetail> dtoListToEntityList(List<ExperienceDetailDto> dtoList) {
        return dtoList.stream().map(this::dtoToEntity).toList();
    }

    @Override
    public List<ExperienceDetailDto> entityListToDtoList(List<ExperienceDetail> entityList) {
        return entityList.stream().map(this::entityToDto).toList();
    }
}
