package ru.rsh12.resume.repository;

import org.springframework.data.repository.CrudRepository;
import ru.rsh12.resume.entity.SkillSet;

import java.util.List;

public interface SkillSetRepository extends CrudRepository<SkillSet, Integer> {

    List<SkillSet> findByIdIn(List<Integer> ids);

}
