package ru.rsh12.job.service.mapper;
/*
 * Date: 18.04.2022
 * Time: 9:46 AM
 * */

import org.springframework.stereotype.Component;
import ru.rsh12.api.core.job.dto.JobTypeDto;
import ru.rsh12.job.entity.JobType;
import ru.rsh12.util.mapper.Mapper;

@Component
public class JobTypeMapper implements Mapper<JobType, JobTypeDto> {

    @Override
    public JobType dtoToEntity(JobTypeDto dto) {
        return new JobType(dto.name());
    }

    @Override
    public JobTypeDto entityToDto(JobType entity) {
        return new JobTypeDto(entity.getId(), entity.getName());
    }

}
