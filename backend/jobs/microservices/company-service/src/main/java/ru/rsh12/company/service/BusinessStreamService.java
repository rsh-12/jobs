package ru.rsh12.company.service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.rsh12.company.entity.BusinessStream;

public interface BusinessStreamService {

    /**
     * Gets business stream by id.
     *
     * @param id the id
     * @return {@link BusinessStream}
     */
    Optional<BusinessStream> findById(Integer id);

    /**
     * Gets business stream by name.
     *
     * @param name the business stream name
     * @return {@link BusinessStream}
     */
    Optional<BusinessStream> findByName(String name);

    /**
     * Gets business streams containing the value of the parameter in their names.
     *
     * @param name     the business stream name
     * @param pageable {@link Pageable}
     * @return the {@link Page} of {@link BusinessStream business streams}
     */
    Page<BusinessStream> findByName(String name, Pageable pageable);

    /**
     * Gets business streams containing the value of the parameter in their names.
     *
     * @param name  the business stream name
     * @param limit the maximum possible number of records
     * @return the {@link List} of {@link BusinessStream business streams}
     */
    List<BusinessStream> findByName(String name, int limit);

    Page<BusinessStream> findAll(Pageable pageable);

    void deleteById(int id);

    void delete(BusinessStream businessStream);

}
