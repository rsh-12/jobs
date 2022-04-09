package ru.rsh12.company.repository;
/*
 * Date: 06.04.2022
 * Time: 9:47 PM
 * */

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
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

@Sql(scripts = {"industries.sql"})
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class BusinessStreamRepositoryTest extends PostgreSqlTestBase {

    @Autowired
    private BusinessStreamRepository repository;

    @AfterEach
    void tearDown() {
        assertTrue(repository.count() > 0);
        repository.deleteAll();
        assertEquals(0, repository.count());
    }

    @Test
    public void findByName() {
        assertTrue(repository.findByName("Automotive Business").isPresent());
    }

    @Test
    public void findByNameContains_WithLimit() {
        List<BusinessStream> entities = repository.findByNameContains("business", 10);
        assertFalse(entities.isEmpty());
        assertEquals(1, entities.size());
    }

    @Test
    public void findByNameContains_WithPageable() {
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
