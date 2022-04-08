package ru.rsh12.company.repository;
/*
 * Date: 06.04.2022
 * Time: 9:47 PM
 * */

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ru.rsh12.company.PostgreSqlTestBase;
import ru.rsh12.company.entity.BusinessStream;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class BusinessStreamRepositoryTest extends PostgreSqlTestBase {

    @Autowired
    private BusinessStreamRepository repository;

    private BusinessStream savedEntity;

    @BeforeEach
    void setUp() {
        repository.deleteAll();

        BusinessStream entity = new BusinessStream("Automotive Business");
        savedEntity = repository.save(entity);

        assertEquals(savedEntity, entity);
    }

    @Test
    public void findByName() {
        assertEquals(repository.count(), 1);
        assertTrue(repository.findByName(savedEntity.getName()).isPresent());
    }

    @Test
    public void findByNameContains_WithLimit() {
        List<BusinessStream> entities = repository.findByNameContains("business", 10);
        assertFalse(entities.isEmpty());
        assertEquals(1, entities.size());
        assertEquals(savedEntity, entities.get(0));
    }

    @Test
    public void findByNameContains_WithPageable() {
        PageRequest pr = PageRequest.of(0, 10);
        Page<BusinessStream> page = repository.findByNameContains("business", pr);

        assertEquals(1, page.getTotalElements());
        assertEquals(1, page.getTotalPages());

        assertFalse(page.getContent().isEmpty());
        assertEquals(savedEntity, page.getContent().get(0));
    }

}
