package ru.rsh12.job.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.rsh12.api.core.job.api.JobPostApi;
import ru.rsh12.api.core.job.request.JobPostRequest;
import ru.rsh12.api.event.Event;
import ru.rsh12.api.exceptions.EventProcessingException;

import java.util.function.Consumer;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class MessageProcessorConfig {

    private final JobPostApi jobPostApi;

    @Bean
    public Consumer<Event<Integer, JobPostRequest>> jobPostMessageProcessor() {
        return event -> {
            log.info("Process job post message created at '{}'", event.getEventCreatedAt());

            switch (event.getEventType()) {
                case CREATE -> {
                    JobPostRequest request = event.getData();
                    log.info("Create job post with name '{}'", request.getTitle());
                    jobPostApi.createJobPost(request.getCompanyId(), request).block();
                }

                case UPDATE -> {
                    JobPostRequest request = event.getData();
                    log.info("Update job post with name '{}'", request.getTitle());
                    jobPostApi.updateJobPost(request.getCompanyId(), event.getKey(), request).block();
                }

                case DELETE -> {
                    log.info("Delete job post with id '{}'", event.getKey());
                    jobPostApi.deleteJobPost(event.getKey()).block();
                }

                default -> {
                    String errorMessage = "Incorrect event type: %s, expected CREATE, UPDATE or DELETE"
                            .formatted(event.getEventType());
                    log.warn(errorMessage);
                    throw new EventProcessingException(errorMessage);
                }
            }

            log.info("Message processing done!");
        };
    }
}
