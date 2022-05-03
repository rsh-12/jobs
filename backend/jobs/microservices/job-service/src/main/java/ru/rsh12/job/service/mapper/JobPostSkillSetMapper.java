package ru.rsh12.job.service.mapper;
/*
 * Date: 18.04.2022
 * Time: 10:13 AM
 * */

import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Component;
import ru.rsh12.api.core.job.dto.JobPostSkillSetDto;
import ru.rsh12.job.entity.JobPostSkillSet;
import ru.rsh12.util.mapper.Mapper;

@Component
public class JobPostSkillSetMapper implements Mapper<JobPostSkillSet, JobPostSkillSetDto> {

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

    public Set<JobPostSkillSetDto> entitySetToDtoSey(Set<JobPostSkillSet> entities) {
        return entities.stream().map(this::entityToDto).collect(Collectors.toSet());
    }

}
