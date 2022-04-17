package ru.rsh12.job.repository;
/*
 * Date: 15.04.2022
 * Time: 7:14 PM
 * */

import org.springframework.data.repository.CrudRepository;
import ru.rsh12.job.entity.JobPostActivity;
import ru.rsh12.job.entity.JobPostActivityId;

public interface JobPostActivityRepository
        extends CrudRepository<JobPostActivity, JobPostActivityId> {

}
