package ru.rsh12.job.service.mapper;
/*
 * Date: 18.04.2022
 * Time: 9:48 AM
 * */

import org.springframework.stereotype.Component;
import ru.rsh12.api.core.job.dto.JobLocationDto;
import ru.rsh12.job.entity.JobLocation;
import ru.rsh12.util.mapper.Mapper;

@Component
public class JobLocationMapper implements Mapper<JobLocation, JobLocationDto> {

    @Override
    public JobLocation dtoToEntity(JobLocationDto dto) {
        return new JobLocation(
                dto.street(),
                dto.city(),
                dto.state(),
                dto.country());
    }

    @Override
    public JobLocationDto entityToDto(JobLocation entity) {
        return new JobLocationDto(
                entity.getId(),
                entity.getStreet(),
                entity.getCity(),
                entity.getState(),
                entity.getCountry());
    }

}
