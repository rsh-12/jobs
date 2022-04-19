package ru.rsh12.job.service.mapper;
/*
 * Date: 18.04.2022
 * Time: 10:13 AM
 * */

import org.springframework.stereotype.Component;
import ru.rsh12.api.core.job.dto.JobPostSkillSetDto;
import ru.rsh12.job.entity.JobPostSkillSet;
import ru.rsh12.util.Mapper;

@Component
public class JobPostSkillSetMapper implements Mapper<JobPostSkillSet, JobPostSkillSetDto> {

    @Override
    public JobPostSkillSet dtoToEntity(JobPostSkillSetDto dto) {
        return null;
    }

    @Override
    public JobPostSkillSetDto entityToDto(JobPostSkillSet entity) {
        return null;
    }

}
