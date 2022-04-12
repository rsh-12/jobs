package ru.rsh12.company.service;
/*
 * Date: 10.04.2022
 * Time: 6:51 PM
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
import ru.rsh12.company.entity.BusinessStream;
import ru.rsh12.company.repository.BusinessStreamRepository;
import ru.rsh12.company.service.impl.BusinessStreamServiceImpl;

@ExtendWith(SpringExtension.class)
public class BusinessStreamServiceTest {

    @InjectMocks
    private BusinessStreamServiceImpl service;

    @Mock
    private BusinessStreamRepository repository;

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

        Mono<BusinessStream> monoEntity = service.findOne(1);
        StepVerifier.create(monoEntity).verifyComplete();
    }

    @Test
    public void findOne_ShouldReturnData() {
        BusinessStream mockEntity = mock(BusinessStream.class);
        given(mockEntity.getName()).willReturn("Food Products");

        given(repository.findById(anyInt())).willReturn(Optional.of(mockEntity));

        Mono<BusinessStream> monoEntity = service.findOne(1);
        StepVerifier.create(monoEntity)
                .thenConsumeWhile(industry -> industry.getName().equals("Food Products"))
                .verifyComplete();
    }

    @Test
    public void findAll_ShouldBeCompleted() {
        given(repository.findAll(any(Pageable.class)))
                .willReturn(Page.empty());

        Flux<BusinessStream> flux = service.findAll(Pageable.unpaged());
        StepVerifier.create(flux)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    public void findAll_ShouldReturnData() {
        BusinessStream mockEntity = mock(BusinessStream.class);
        given(mockEntity.getName()).willReturn("Sample");

        given(repository.findAll(any(Pageable.class)))
                .willReturn(new PageImpl<>(List.of(mockEntity)));

        Flux<BusinessStream> flux = service.findAll(PageRequest.of(0, 10));
        StepVerifier.create(flux)
                .expectNextCount(1)
                .thenConsumeWhile(company -> company.getName().equals("Sample"))
                .verifyComplete();
    }

}
