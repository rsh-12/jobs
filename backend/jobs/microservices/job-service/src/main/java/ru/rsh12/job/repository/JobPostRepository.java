package ru.rsh12.job.repository;
/*
 * Date: 15.04.2022
 * Time: 7:13 PM
 * */

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.rsh12.job.entity.JobPost;

public interface JobPostRepository extends PagingAndSortingRepository<JobPost, Integer> {

}
