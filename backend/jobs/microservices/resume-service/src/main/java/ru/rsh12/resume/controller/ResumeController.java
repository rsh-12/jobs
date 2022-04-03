package ru.rsh12.resume.controller;
/*
 * Date: 03.04.2022
 * Time: 11:28 AM
 * */

import java.time.Instant;
import org.springframework.web.bind.annotation.RestController;
import ru.rsh12.api.core.resume.api.ResumeApi;
import ru.rsh12.api.core.resume.dto.ResumeDto;

@RestController
public class ResumeController implements ResumeApi {

    @Override
    public ResumeDto getResume(Integer resumeId) {
        return new ResumeDto(1,
                "My bio",
                null,
                "RUB",
                "Manager",
                "someAccountIdFromAuthServer",
                Instant.now(),
                Instant.now());
    }

}
