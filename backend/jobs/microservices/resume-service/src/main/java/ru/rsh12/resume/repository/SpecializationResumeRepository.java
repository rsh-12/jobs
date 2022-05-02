package ru.rsh12.resume.repository;

import org.springframework.data.repository.CrudRepository;
import ru.rsh12.resume.entity.SpecializationResume;
import ru.rsh12.resume.entity.SpecializationResumeId;

public interface SpecializationResumeRepository
        extends CrudRepository<SpecializationResume, SpecializationResumeId> {

}
