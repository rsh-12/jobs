package ru.rsh12.job.repository;

import org.springframework.data.repository.CrudRepository;
import ru.rsh12.job.entity.JobLocation;

public interface JobLocationRepository extends CrudRepository<JobLocation, Integer> {

}
