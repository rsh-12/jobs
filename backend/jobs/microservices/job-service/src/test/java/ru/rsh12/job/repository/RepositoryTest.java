package ru.rsh12.job.repository;
/*
 * Date: 15.04.2022
 * Time: 9:48 PM
 * */


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import ru.rsh12.job.PostgreSqlTestBase;
import ru.rsh12.job.entity.JobPost;
import ru.rsh12.job.entity.JobType;
import ru.rsh12.job.entity.Specialization;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @AfterEach
    void tearDown() {
        jobPostSkillSetRepository.deleteAll();
        jobPostActivityRepository.deleteAll();
        jobPostRepository.deleteAll();
        specializationRepository.deleteAll();
        jobLocationRepository.deleteAll();
        jobTypeRepository.deleteAll();
    }

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

    @Test
    public void findByTypeNameIgnoreCase() {
        List<JobPost> jobs = jobPostRepository.findByTypeNameIgnoreCase("full time");
        assertFalse(jobs.isEmpty());
        assertEquals(3, jobs.size());

        jobs = jobPostRepository.findByTypeNameIgnoreCase("part time");
        assertTrue(jobs.isEmpty());
    }

    @Test
    public void addJobPost() {
        Optional<JobType> optionalPartTimeJob = jobTypeRepository.findByNameIgnoreCase("part time");
        assertTrue(optionalPartTimeJob.isPresent());

        JobType partTime = optionalPartTimeJob.get();
        assertTrue(partTime.getJobs().isEmpty());

        Optional<Boolean> opt = jobPostRepository.findById(1).map(partTime::addJobPost);
        assertTrue(opt.isPresent());

        assertFalse(partTime.getJobs().isEmpty());
        List<JobPost> partTimeJobs = jobPostRepository.findByTypeNameIgnoreCase("part time");
        assertFalse(partTimeJobs.isEmpty());
    }

    @Test
    void findByPostedById() {
        Page<JobPost> jobPostPage = jobPostRepository.findByPostedById(10, PageRequest.of(0, 10));
        assertFalse(jobPostPage.isEmpty());
        assertEquals(2, jobPostPage.getTotalElements());
    }

    @Test
    void findSpecializationsById() {
        assertEquals(4, specializationRepository.count());

        List<Specialization> specializations = specializationRepository.findByIdIn(List.of(1, 3));
        assertFalse(specializations.isEmpty());
        assertEquals(2, specializations.size());
    }

}