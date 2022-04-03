package ru.rsh12.api.core.job.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rsh12.api.core.job.dto.JobPostDto;

@RequestMapping("/api/v1/jobs")
public interface JobPostApi {

    @GetMapping(value = "/{jobPostId}", produces = "application/json")
    JobPostDto getJobPost(@PathVariable Integer jobPostId);

}
