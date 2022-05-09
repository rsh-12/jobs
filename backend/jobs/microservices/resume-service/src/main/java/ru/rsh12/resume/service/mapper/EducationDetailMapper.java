package ru.rsh12.resume.service.mapper;

import org.springframework.stereotype.Component;
import ru.rsh12.api.core.resume.dto.EducationDetailDto;
import ru.rsh12.resume.entity.EducationDetail;
import ru.rsh12.util.mapper.CommonListMapper;

import java.util.List;

@Component
public class EducationDetailMapper implements CommonListMapper<EducationDetail, EducationDetailDto> {

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
    public List<EducationDetail> dtoListToEntityList(List<EducationDetailDto> dtoList) {
        return dtoList.stream().map(this::dtoToEntity).toList();
    }

    @Override
    public List<EducationDetailDto> entityListToDtoList(List<EducationDetail> entityList) {
        return entityList.stream().map(this::entityToDto).toList();
    }
}
