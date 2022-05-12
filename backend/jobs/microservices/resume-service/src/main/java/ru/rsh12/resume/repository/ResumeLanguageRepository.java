package ru.rsh12.resume.repository;

import org.springframework.data.repository.CrudRepository;
import ru.rsh12.resume.entity.ResumeLanguage;
import ru.rsh12.resume.entity.ResumeLanguageId;

public interface ResumeLanguageRepository extends CrudRepository<ResumeLanguage, ResumeLanguageId> {
}
