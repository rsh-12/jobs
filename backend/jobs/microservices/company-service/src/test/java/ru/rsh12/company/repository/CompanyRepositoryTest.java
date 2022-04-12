package ru.rsh12.company.repository;
/*
 * Date: 09.04.2022
 * Time: 8:09 AM
 * */

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
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
import ru.rsh12.company.entity.CompanyImage;

@Sql(scripts = {"companies.sql"})
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CompanyRepositoryTest extends PostgreSqlTestBase {

    @Autowired
    private CompanyRepository repository;

    @Autowired
    private CompanyImageRepository companyImageRepository;

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

        Optional<Company> optionalEntity = repository.findByName("Zoo");
        assertTrue(optionalEntity.isPresent());

        repository.deleteById(optionalEntity.get().getId());

        assertEquals(before - 1, repository.count());
    }

    @Test
    public void save_ShouldSaveImagesToo() {
        Optional<Company> optionalEntity = repository.findByName("SomeName");
        assertFalse(optionalEntity.isPresent());

        assertEquals(0, companyImageRepository.count());

        List<CompanyImage> images = IntStream.rangeClosed(1, 5)
                .mapToObj(i -> new CompanyImage("path/to/image/" + i))
                .toList();

        Company entity = new Company();
        entity.setName("SomeName");
        entity.setImages(images);

        Company savedEntity = repository.save(entity);
        assertEquals(images.size(), savedEntity.getImages().size());

        assertEquals(images.size(), companyImageRepository.findByCompany(savedEntity).size());
    }

}
