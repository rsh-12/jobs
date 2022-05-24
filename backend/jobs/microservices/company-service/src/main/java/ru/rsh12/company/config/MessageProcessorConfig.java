package ru.rsh12.company.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.rsh12.api.core.company.api.BusinessStreamApi;
import ru.rsh12.api.core.company.api.CompanyApi;
import ru.rsh12.api.core.company.request.CompanyRequest;
import ru.rsh12.api.core.company.request.BusinessStreamRequest;
import ru.rsh12.api.event.Event;
import ru.rsh12.api.exceptions.EventProcessingException;

import java.util.function.Consumer;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class MessageProcessorConfig {

    private final CompanyApi companyApi;
    private final BusinessStreamApi businessStreamApi;

    @Bean
    public Consumer<Event<Integer, CompanyRequest>> companyMessageProcessor() {
        return event -> {
            log.info("Process company message created at '{}'", event.getEventCreatedAt());

            switch (event.getEventType()) {
                case CREATE -> {
                    CompanyRequest request = event.getData();
                    log.info("Create company with name '{}'", request.getName());
                    companyApi.createCompany(request.getBusinessStreamId(), request).block();
                }

                case UPDATE -> {
                    CompanyRequest request = event.getData();
                    log.info("Update company with name '{}'", request.getName());
                    companyApi.updateCompany(request.getBusinessStreamId(), event.getKey(), request).block();
                }

                case DELETE -> {
                    log.info("Delete company by id '{}'", event.getKey());
                    companyApi.deleteCompany(event.getKey()).block();
                }

                default -> {
                    String errorMessage = "Incorrect event type: %s, expected CREATE or DELETE"
                            .formatted(event.getEventType());
                    log.warn(errorMessage);
                    throw new EventProcessingException(errorMessage);
                }
            }

            log.info("Message processing done!");
        };
    }

    @Bean
    public Consumer<Event<Integer, BusinessStreamRequest>> businessStreamMessageProcessor() {
        return event -> {
            log.info("Process business stream message created at '{}'", event.getEventCreatedAt());

            switch (event.getEventType()) {
                case CREATE -> {
                    log.info("Create business stream with name '{}'", event.getData().getName());
                    businessStreamApi.createBusinessStream(event.getData()).block();
                }

                case UPDATE -> {
                    throw new NotImplementedException("No update implementation found!");
                }

                case DELETE -> {
                    throw new NotImplementedException("No delete implementation found!");
                }

                default -> {
                    String errorMessage = "Incorrect event type: %s, expected CREATE or DELETE"
                            .formatted(event.getEventType());
                    log.warn(errorMessage);
                    throw new EventProcessingException(errorMessage);
                }
            }

            log.info("Message processing done!");
        };
    }

}
