package ru.rsh12.job.repository;

import org.springframework.data.repository.CrudRepository;
import ru.rsh12.job.entity.Specialization;

import java.util.List;

public interface SpecializationRepository extends CrudRepository<Specialization, Integer> {

    List<Specialization> findByIdIn(List<Integer> ids);

}
