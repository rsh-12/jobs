package ru.rsh12.company.service;
/*
 * Date: 10.04.2022
 * Time: 6:51 PM
 * */

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;
import ru.rsh12.api.exceptions.NotFoundException;
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

    @Test
    public void findOne_ShouldReturnBusinessStreamDto() {
        BusinessStream mockEntity = new BusinessStream("Food Products");
        mockEntity.setId(1);

        given(repository.findById(anyInt())).willReturn(Optional.empty());
        given(jdbcScheduler.createWorker()).willReturn(Schedulers.single().createWorker());

        Mono<BusinessStream> monoEntity = service.findOne(1);
        StepVerifier.create(monoEntity)
                .expectError(NotFoundException.class)
                .verify();
    }

}
