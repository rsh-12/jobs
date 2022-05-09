package ru.rsh12.job.service.mapper;
/*
 * Date: 18.04.2022
 * Time: 9:30 AM
 * */

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.rsh12.api.core.job.dto.JobPostDto;
import ru.rsh12.job.entity.JobPost;
import ru.rsh12.util.ServiceUtil;
import ru.rsh12.util.mapper.Mapper;

@Component
@RequiredArgsConstructor
public class JobPostMapper implements Mapper<JobPost, JobPostDto> {

    private final JobTypeMapper jobTypeMapper;
    private final JobLocationMapper jobLocationMapper;
    private final JobPostSkillSetMapper jobPostSkillSetMapper;
    private final SpecializationMapper specializationMapper;

    private final ServiceUtil serviceUtil;

    @Override
    public JobPost dtoToEntity(JobPostDto dto) {
        JobPost entity = new JobPost();

        entity.setTitle(dto.title());
        entity.setDescription(dto.description());

        entity.setActive(dto.isActive());
        entity.setSalaryFrom(dto.salaryFrom());

        entity.setSalaryUpTo(dto.salaryUpTo());
        entity.setCurrency(dto.currency() == null ? "RUB" : dto.currency());

        entity.setEmail(dto.email());
        entity.setPhone(dto.phone());

        entity.setType(jobTypeMapper.dtoToEntity(dto.type()));
        entity.setLocation(jobLocationMapper.dtoToEntity(dto.location()));

        entity.setPostedById(dto.postedById());
        entity.setSpecializations(specializationMapper.dtoListToEntityList(dto.specializations()));

        return entity;
    }

    @Override
    public JobPostDto entityToDto(JobPost entity) {

        return new JobPostDto(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.isActive(),
                entity.getSalaryFrom(),
                entity.getSalaryUpTo(),
                entity.getCurrency(),
                entity.getEmail(),
                entity.getPhone(),
                jobTypeMapper.entityToDto(entity.getType()),
                jobLocationMapper.entityToDto(entity.getLocation()),
                entity.getPostedById(),
                specializationMapper.entityListToDtoList(entity.getSpecializations()),
                jobPostSkillSetMapper.entityListToDtoList(entity.getSkills()),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                serviceUtil.getServiceAddress()
        );
    }

}
