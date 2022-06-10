package ru.rsh12.resume.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import ru.rsh12.resume.PostgreSqlTestBase;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Sql(scripts = {"data.sql"})
@DataJpaTest(properties = {
        "spring.cloud.stream.default-binder=rabbit",
        "logging.level.ru.rsh12=debug",
        "eureka.client.enabled=false",
        "spring.cloud.config.enabled=false"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RepositoryTest extends PostgreSqlTestBase {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private EducationDetailRepository educationDetailRepository;

    @Autowired
    private ExperienceDetailRepository experienceDetailRepository;

    @Autowired
    private ResumeSkillSetRepository resumeSkillSetRepository;

    @Autowired
    private SkillSetRepository skillSetRepository;

    @Autowired
    private SpecializationResumeRepository specializationResumeRepository;

    @AfterEach
    void tearDown() {
        countryRepository.deleteAll();
        educationDetailRepository.deleteAll();
        experienceDetailRepository.deleteAll();
        resumeSkillSetRepository.deleteAll();
        skillSetRepository.deleteAll();
        specializationResumeRepository.deleteAll();
    }

    @Test
    void count_ShouldInitDmlScript() {
        assertEquals(15, countryRepository.count());
        assertEquals(3, educationDetailRepository.count());
        assertEquals(3, experienceDetailRepository.count());
        assertEquals(9, resumeSkillSetRepository.count());
        assertEquals(9, skillSetRepository.count());
        assertEquals(3, specializationResumeRepository.count());
    }

}
