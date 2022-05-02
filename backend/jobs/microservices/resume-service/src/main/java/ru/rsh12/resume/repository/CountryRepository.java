package ru.rsh12.resume.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rsh12.resume.entity.Country;

public interface CountryRepository extends JpaRepository<Country, Integer> {
}
