package ru.rsh12.job.repository;
/*
 * Date: 15.04.2022
 * Time: 9:48 PM
 * */


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import ru.rsh12.job.PostgreSqlTestBase;
import ru.rsh12.job.entity.JobPost;

@Sql(scripts = {"data.sql"})
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class RepositoryTest extends PostgreSqlTestBase {

    @Autowired
    private JobTypeRepository jobTypeRepository;

    @Autowired
    private JobLocationRepository jobLocationRepository;

    @Autowired
    private JobPostRepository jobPostRepository;

    @Autowired
    private JobPostSkillSetRepository jobPostSkillSetRepository;

    @Autowired
    private JobPostActivityRepository jobPostActivityRepository;

    @Autowired
    private SpecializationRepository specializationRepository;

    @Test
    public void count_ShouldInitDmlScript() {
        assertEquals(2, jobTypeRepository.count());
        assertEquals(2, jobLocationRepository.count());
        assertEquals(3, jobPostRepository.count());
        assertEquals(6, jobPostSkillSetRepository.count());
        assertEquals(3, jobPostActivityRepository.count());
        assertEquals(4, specializationRepository.count());
    }

    @Test
    public void findBySpecializationsIds() {
        List<JobPost> jobs = jobPostRepository.findBySpecializationsIds(List.of(1, 6, 10));
        assertFalse(jobs.isEmpty());
        assertEquals(1, jobs.size());

        JobPost jobPost = jobs.get(0);
        assertEquals("Frontend Developer", jobPost.getTitle());
    }

}