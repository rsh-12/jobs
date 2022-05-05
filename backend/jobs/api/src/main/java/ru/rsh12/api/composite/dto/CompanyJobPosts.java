package ru.rsh12.api.composite.dto;

import ru.rsh12.api.core.company.dto.CompanyDto;
import ru.rsh12.api.core.job.dto.JobPostDto;

import java.util.List;

public record CompanyJobPosts(
        CompanyDto company,
        List<JobPostDto> jobPosts,
        String companyServiceAddress,
        String jobServiceAddress) {

}
