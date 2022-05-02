package ru.rsh12.resume.service.mapper;

import org.springframework.stereotype.Component;
import ru.rsh12.api.core.resume.dto.ExperienceDetailDto;
import ru.rsh12.resume.entity.ExperienceDetail;
import ru.rsh12.util.CommonSetMapper;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ExperienceDetailMapper implements CommonSetMapper<ExperienceDetail, ExperienceDetailDto> {

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
    public Set<ExperienceDetail> dtoSetToEntitySet(Set<ExperienceDetailDto> dtoSet) {
        return dtoSet.stream()
                .map(this::dtoToEntity)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<ExperienceDetailDto> entitySetToDtoSet(Set<ExperienceDetail> entitySet) {
        return entitySet.stream()
                .map(this::entityToDto)
                .collect(Collectors.toSet());
    }

}
