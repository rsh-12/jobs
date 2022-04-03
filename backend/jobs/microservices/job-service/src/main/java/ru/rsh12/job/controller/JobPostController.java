package ru.rsh12.job.controller;
/*
 * Date: 03.04.2022
 * Time: 11:24 AM
 * */

import java.time.Instant;
import org.springframework.web.bind.annotation.RestController;
import ru.rsh12.api.core.job.api.JobPostApi;
import ru.rsh12.api.core.job.dto.JobPostDto;

@RestController
public class JobPostController implements JobPostApi {

    @Override
    public JobPostDto getJobPost(Integer jobPostId) {
        return new JobPostDto(1,
                "Frontend Developer",
                "Some description",
                true,
                null,
                5000,
                "USD",
                "my@mail.com",
                "8-800-900-90-90",
                2,
                3,
                4,
                Instant.now(),
                Instant.now());
    }

}
