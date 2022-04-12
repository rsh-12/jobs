package ru.rsh12.company.service;
/*
 * Date: 12.04.2022
 * Time: 4:33 PM
 * */

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;
import ru.rsh12.company.entity.Company;
import ru.rsh12.company.repository.CompanyRepository;
import ru.rsh12.company.service.impl.CompanyServiceImpl;

@ExtendWith(SpringExtension.class)
public class CompanyServiceTest {

    @InjectMocks
    private CompanyServiceImpl service;

    @Mock
    private CompanyRepository repository;

    @Mock
    private Scheduler jdbcScheduler;

    @BeforeEach
    void setUp() {
        given(jdbcScheduler.createWorker())
                .willReturn(Schedulers.single().createWorker());
    }

    @Test
    public void findOne_ShouldBeCompleted() {
        given(repository.findById(anyInt())).willReturn(Optional.empty());

        Mono<Company> monoEntity = service.findOne(1);
        StepVerifier.create(monoEntity).verifyComplete();
    }

    @Test
    public void findOne_ShouldReturnData() {
        Company mockEntity = mock(Company.class);
        given(mockEntity.getName()).willReturn("MyDocs");

        given(repository.findById(anyInt())).willReturn(Optional.of(mockEntity));

        Mono<Company> monoEntity = service.findOne(1);
        StepVerifier.create(monoEntity)
                .thenConsumeWhile(company -> company.getName().equals("MyDocs"))
                .verifyComplete();
    }

    @Test
    public void findAll_ShouldBeCompleted() {
        given(repository.findAll(any(Pageable.class)))
                .willReturn(Page.empty());

        Flux<Company> flux = service.findAll(PageRequest.of(0, 10));
        StepVerifier.create(flux).verifyComplete();
    }

    @Test
    public void findAll_ShouldReturnData() {
        Company mockEntity = mock(Company.class);
        given(mockEntity.getName()).willReturn("Sample");

        given(repository.findAll(any(Pageable.class)))
                .willReturn(new PageImpl<>(List.of(mockEntity)));

        Flux<Company> flux = service.findAll(PageRequest.of(0, 10));
        StepVerifier.create(flux)
                .thenConsumeWhile(company -> company.getName().equals("Sample"))
                .verifyComplete();
    }

}
