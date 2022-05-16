package ru.rsh12.resume.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.rsh12.api.core.resume.api.ResumeApi;
import ru.rsh12.api.core.resume.request.ResumeRequest;
import ru.rsh12.api.event.Event;
import ru.rsh12.api.exceptions.EventProcessingException;

import java.util.function.Consumer;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class MessageProcessorConfig {

    private final ResumeApi resumeApi;

    @Bean
    public Consumer<Event<Integer, ResumeRequest>> resumeMessageProcessor() {
        return event -> {
            log.info("Process resume message created at '{}'", event.getEventCreatedAt());

            final Integer resumeId = event.getKey();
            switch (event.getEventType()) {
                case CREATE -> {
                    ResumeRequest request = event.getData();
                    log.info("Create resume with desired position '{}'", request.getDesiredJobPosition());
                    resumeApi.createResume(request).block();
                }

                case UPDATE -> {
                    log.info("Update resume by ID='{}'", resumeId);
                    resumeApi.updateResume(resumeId, event.getData()).block();
                }

                case DELETE -> {
                    log.info("Delete resume by ID='{}'", resumeId);
                    resumeApi.deleteResume(resumeId).block();
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
