package ru.rsh12.job.repository;

import org.springframework.data.repository.CrudRepository;
import ru.rsh12.job.entity.JobPostSkillSet;
import ru.rsh12.job.entity.JobPostSkillSetId;

public interface JobPostSkillSetRepository
        extends CrudRepository<JobPostSkillSet, JobPostSkillSetId> {

}
