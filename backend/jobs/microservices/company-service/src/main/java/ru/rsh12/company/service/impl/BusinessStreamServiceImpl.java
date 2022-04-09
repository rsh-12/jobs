package ru.rsh12.company.service.impl;
/*
 * Date: 09.04.2022
 * Time: 10:59 PM
 * */

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.rsh12.company.entity.BusinessStream;
import ru.rsh12.company.repository.BusinessStreamRepository;
import ru.rsh12.company.service.BusinessStreamService;

@Slf4j
@Service
@RequiredArgsConstructor
public class BusinessStreamServiceImpl implements BusinessStreamService {

    private final BusinessStreamRepository repository;

    @Override
    public Optional<BusinessStream> getBusinessStream(Integer id) {
        log.debug("");

        return repository.findById(id);
    }

    @Override
    public Optional<BusinessStream> getBusinessStream(String name) {
        log.debug("");

        return repository.findByName(name);
    }

    @Override
    public Page<BusinessStream> getBusinessStreams(String name, Pageable pageable) {
        log.debug("");

        return repository.findByNameContainsIgnoreCase(name, pageable);
    }

    @Override
    public List<BusinessStream> getBusinessStreams(String name, int limit) {
        log.debug("");

        return repository.findByNameContainsLimit(name, limit);
    }

}
