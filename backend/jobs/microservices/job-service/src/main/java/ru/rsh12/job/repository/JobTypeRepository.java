package ru.rsh12.job.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import ru.rsh12.job.entity.JobType;

public interface JobTypeRepository extends CrudRepository<JobType, Integer> {

    Optional<JobType> findByNameIgnoreCase(String name);

}
