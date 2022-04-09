package ru.rsh12.company.repository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.rsh12.company.entity.Company;

public interface CompanyRepository extends PagingAndSortingRepository<Company, Integer> {

    Optional<Company> findByName(String name);

    Page<Company> findByNameContainsIgnoreCase(String name, Pageable pageable);

}
