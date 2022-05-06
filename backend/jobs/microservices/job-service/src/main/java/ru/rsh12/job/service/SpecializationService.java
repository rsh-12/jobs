package ru.rsh12.job.service;

import reactor.core.publisher.Flux;
import ru.rsh12.job.entity.Specialization;

import java.util.List;

public interface SpecializationService {

    Flux<Specialization> findAllById(List<Integer> specializationIds);

}
