package ru.rsh12.resume.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rsh12.resume.entity.Resume;

public interface ResumeRepository extends JpaRepository<Resume, Integer> {
}
