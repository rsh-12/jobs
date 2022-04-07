package ru.rsh12.company.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.rsh12.company.entity.Company;

public interface CompanyRepository extends
        PagingAndSortingRepository<Company, Integer> {

}
