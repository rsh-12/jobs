package ru.rsh12.job.service.mapper;
/*
 * Date: 18.04.2022
 * Time: 10:13 AM
 * */

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Component;
import ru.rsh12.api.core.job.dto.JobPostSkillSetDto;
import ru.rsh12.job.entity.JobPostSkillSet;
import ru.rsh12.util.mapper.CommonListMapper;

import java.util.List;

@Component
public class JobPostSkillSetMapper implements CommonListMapper<JobPostSkillSet, JobPostSkillSetDto> {

    @Override
    public JobPostSkillSet dtoToEntity(JobPostSkillSetDto dto) {
        throw new NotImplementedException("No implementation found");
    }

    @Override
    public JobPostSkillSetDto entityToDto(JobPostSkillSet entity) {
        return new JobPostSkillSetDto(
                entity.getLevel(),
                entity.getSkillSetId());
    }

    @Override
    public List<JobPostSkillSet> dtoListToEntityList(List<JobPostSkillSetDto> dtoList) {
        return dtoList.stream().map(this::dtoToEntity).toList();
    }

    @Override
    public List<JobPostSkillSetDto> entityListToDtoList(List<JobPostSkillSet> entityList) {
        return entityList.stream().map(this::entityToDto).toList();
    }
}
