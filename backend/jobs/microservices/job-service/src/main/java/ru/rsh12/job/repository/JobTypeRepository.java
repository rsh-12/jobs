package ru.rsh12.job.repository;

import org.springframework.data.repository.CrudRepository;
import ru.rsh12.job.entity.JobType;

public interface JobTypeRepository extends CrudRepository<JobType, Integer> {

}
