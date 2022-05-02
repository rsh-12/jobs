package ru.rsh12.resume.repository;

import org.springframework.data.repository.CrudRepository;
import ru.rsh12.resume.entity.ResumeSkillSet;
import ru.rsh12.resume.entity.ResumeSkillSetId;

public interface ResumeSkillSetRepository extends CrudRepository<ResumeSkillSet, ResumeSkillSetId> {

}
