package ru.rsh12.api.core.resume.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rsh12.api.core.resume.dto.ResumeDto;

@RequestMapping("/api/v1/resume")
public interface ResumeApi {

    @GetMapping(value = "/{resumeId}", produces = "application/json")
    ResumeDto getResume(@PathVariable Integer resumeId);

}
