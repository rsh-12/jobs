package ru.rsh12.company.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.rsh12.company.entity.BusinessStream;

public interface BusinessStreamRepository extends
        PagingAndSortingRepository<BusinessStream, Integer> {

    Optional<BusinessStream> findByName(String name);

    @Query(
            nativeQuery = true,
            countQuery = "SELECT COUNT(*) FROM business_stream",
            value = "SELECT * FROM business_stream WHERE name ~* ?1")
    Page<BusinessStream> findByNameContains(String name, Pageable pageable);

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM business_stream WHERE name ~* ?1 LIMIT ?2")
    List<BusinessStream> findByNameContains(String name, int limit);

}
