package ru.rsh12.company.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.rsh12.company.entity.CompanyImage;

public interface CompanyImageRepository extends
        PagingAndSortingRepository<CompanyImage, Integer> {

}
