package ru.rsh12.api.composite.dto;

import ru.rsh12.api.core.company.dto.CompanyDto;
import ru.rsh12.api.core.job.dto.JobPostDto;

import java.util.Set;

public record CompanyJobPosts(
        CompanyDto company,
        Set<JobPostDto> jobPosts,
        String companyServiceAddress,
        String jobServiceAddress) {

}
