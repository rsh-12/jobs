package ru.rsh12.company.repository;
/*
 * Date: 06.04.2022
 * Time: 9:47 PM
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
import ru.rsh12.company.PostgreSqlTestBase;
import ru.rsh12.company.entity.BusinessStream;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Sql(scripts = {"industries.sql"})
@DataJpaTest(properties = {
        "spring.cloud.stream.default-binder=rabbit",
        "logging.level.ru.rsh12=debug",
        "eureka.client.enabled=false"})
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class BusinessStreamRepositoryTest extends PostgreSqlTestBase {

    @Autowired
    private BusinessStreamRepository repository;

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    public void findByName() {
        assertTrue(repository.findByName("Automotive Business").isPresent());
    }

    @Test
    public void findByNameContainsLimit() {
        List<BusinessStream> entities = repository.findByNameContainsLimit("business", 10);
        assertFalse(entities.isEmpty());
        assertEquals(1, entities.size());
    }

    @Test
    public void findByNameContainsIgnoreCase() {
        PageRequest pr = PageRequest.of(0, 10);
        Page<BusinessStream> page = repository.findByNameContainsIgnoreCase("business", pr);

        assertEquals(1, page.getTotalElements());
        assertEquals(1, page.getTotalPages());
    }

    @Test
    public void deleteById() {
        long before = repository.count();

        String name = "Automotive Business";
        Optional<BusinessStream> optional = repository.findByName(name);
        assertTrue(optional.isPresent());

        repository.deleteById(optional.get().getId());
        assertEquals(before - 1, repository.count());

        assertTrue(repository.findByName(name).isEmpty());
    }

    @Test
    public void save() {
        long before = repository.count();
        repository.save(new BusinessStream("Some industry"));
        assertEquals(before + 1, repository.count());
    }

}
