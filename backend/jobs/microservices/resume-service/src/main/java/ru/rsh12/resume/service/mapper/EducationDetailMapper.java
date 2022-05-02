package ru.rsh12.resume.service.mapper;

import org.springframework.stereotype.Component;
import ru.rsh12.api.core.resume.dto.EducationDetailDto;
import ru.rsh12.resume.entity.EducationDetail;
import ru.rsh12.util.CommonSetMapper;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class EducationDetailMapper implements CommonSetMapper<EducationDetail, EducationDetailDto> {

    @Override
    public EducationDetail dtoToEntity(EducationDetailDto dto) {
        return new EducationDetail(
                dto.institutionName(),
                dto.faculty(),
                dto.startingDate(),
                dto.completionDate(),
                null);
    }

    @Override
    public EducationDetailDto entityToDto(EducationDetail entity) {
        return new EducationDetailDto(
                entity.getId(),
                entity.getInstitutionName(),
                entity.getFaculty(),
                entity.getStartingDate(),
                entity.getCompletionDate());
    }

    @Override
    public Set<EducationDetail> dtoSetToEntitySet(Set<EducationDetailDto> dtoSet) {
        return dtoSet.stream()
                .map(this::dtoToEntity)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<EducationDetailDto> entitySetToDtoSet(Set<EducationDetail> entitySet) {
        return entitySet.stream()
                .map(this::entityToDto)
                .collect(Collectors.toSet());
    }
}
