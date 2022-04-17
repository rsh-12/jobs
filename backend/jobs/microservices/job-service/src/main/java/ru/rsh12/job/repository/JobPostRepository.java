package ru.rsh12.job.repository;
/*
 * Date: 15.04.2022
 * Time: 7:13 PM
 * */

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import ru.rsh12.job.entity.JobPost;

public interface JobPostRepository extends PagingAndSortingRepository<JobPost, Integer> {

    @Query(
            nativeQuery = true,
            value = """
                    SELECT DISTINCT jp.* FROM job_post jp
                    JOIN specialization_job_post sjb ON sjb.job_post_id = jp.id
                    WHERE sjb.specialization_id IN (:ids);
                    """)
    List<JobPost> findBySpecializationsIds(@Param("ids") Iterable<Integer> specializationIds);

}
