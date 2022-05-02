package ru.rsh12.resume.repository;

import org.springframework.data.repository.CrudRepository;
import ru.rsh12.resume.entity.EducationDetail;

public interface EducationDetailRepository extends CrudRepository<EducationDetail, Integer> {
}
