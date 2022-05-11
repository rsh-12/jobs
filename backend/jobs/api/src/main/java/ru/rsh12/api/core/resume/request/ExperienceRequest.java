package ru.rsh12.api.core.resume.request;

import java.time.LocalDate;

public class ExperienceRequest {

    private String jobTitle;
    private String description;
    private String companyName;
    private boolean isCurrentJob;
    private LocalDate startDate;
    private LocalDate endDate;

    public ExperienceRequest(String jobTitle,
                             String description,
                             String companyName,
                             boolean isCurrentJob,
                             LocalDate startDate,
                             LocalDate endDate) {
        this.jobTitle = jobTitle;
        this.description = description;
        this.companyName = companyName;
        this.isCurrentJob = isCurrentJob;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public ExperienceRequest setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ExperienceRequest setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getCompanyName() {
        return companyName;
    }

    public ExperienceRequest setCompanyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public boolean isCurrentJob() {
        return isCurrentJob;
    }

    public ExperienceRequest setCurrentJob(boolean currentJob) {
        isCurrentJob = currentJob;
        return this;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public ExperienceRequest setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public ExperienceRequest setEndDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }
}
