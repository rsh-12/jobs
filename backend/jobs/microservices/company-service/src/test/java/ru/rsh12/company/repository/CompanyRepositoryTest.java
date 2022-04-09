package ru.rsh12.company.repository;
/*
 * Date: 09.04.2022
 * Time: 8:09 AM
 * */

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;
import ru.rsh12.company.PostgreSqlTestBase;
import ru.rsh12.company.entity.Company;

@Sql(scripts = {"data.sql"})
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CompanyRepositoryTest extends PostgreSqlTestBase {

    @Autowired
    private CompanyRepository repository;

    @AfterEach
    void tearDown() {
        assertTrue(repository.count() > 0);
        repository.deleteAll();
        assertEquals(0, repository.count());
    }

    @Test
    public void findByName() {
        assertTrue(repository.findByName("Zoo").isPresent());
    }

    @Test
    public void findByNameContains() {
        Page<Company> page = repository.findByNameContainsIgnoreCase("doc", Pageable.unpaged());
        assertEquals(1, page.getTotalElements());
        assertEquals(1, page.getTotalPages());
    }

    @Test
    public void deleteById() {
        long before = repository.count();
        repository.deleteById(1);
        assertEquals(before - 1, repository.count());
    }

}
