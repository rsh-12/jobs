package ru.rsh12.company.repository;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.rsh12.company.entity.Company;
import ru.rsh12.company.entity.CompanyImage;

public interface CompanyImageRepository extends
        PagingAndSortingRepository<CompanyImage, Integer> {

    List<CompanyImage> findByCompany(Company company);

}
