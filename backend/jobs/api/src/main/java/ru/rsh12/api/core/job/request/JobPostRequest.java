package ru.rsh12.api.core.job.request;

import ru.rsh12.api.core.job.dto.JobPostSkillSetDto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class JobPostRequest {

    @NotBlank(message = "Title must not be blank")
    private String title;

    private String description;

    private boolean active;

    @Min(0)
    private Integer salaryFrom;

    @Min(0)
    private Integer salaryUpTo;

    @Size(min = 3, max = 3)
    private String currency = "RUB";

    @Size(max = 50, message = "Email length must be less than or equal to 50")
    @Pattern(
            flags = {Pattern.Flag.CASE_INSENSITIVE},
            regexp = ".+@.+\\.+\\w{2,8}",
            message = "Invalid email address format")
    private String email;

    @Size(max = 50, message = "Phone length must be less than or equal to 50")
    @Pattern(
            regexp = "\\+?\\d([\\s-]?\\d{3}){0,2}([\\s-]?\\d{2}){2}",
            message = "Invalid phone number format")
    private String phone;

    private Integer jobTypeId;

    private Integer locationId;

    private Integer companyId;

    private List<Integer> specializationIds = new ArrayList<>();

    private List<JobPostSkillSetDto> skills = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public JobPostRequest setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public JobPostRequest setDescription(String description) {
        this.description = description;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public JobPostRequest setActive(boolean active) {
        this.active = active;
        return this;
    }

    public Integer getSalaryFrom() {
        return salaryFrom;
    }

    public JobPostRequest setSalaryFrom(Integer salaryFrom) {
        this.salaryFrom = salaryFrom;
        return this;
    }

    public Integer getSalaryUpTo() {
        return salaryUpTo;
    }

    public JobPostRequest setSalaryUpTo(Integer salaryUpTo) {
        this.salaryUpTo = salaryUpTo;
        return this;
    }

    public String getCurrency() {
        return currency;
    }

    public JobPostRequest setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public JobPostRequest setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public JobPostRequest setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public Integer getJobTypeId() {
        return jobTypeId;
    }

    public JobPostRequest setJobTypeId(Integer jobTypeId) {
        this.jobTypeId = jobTypeId;
        return this;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public JobPostRequest setLocationId(Integer locationId) {
        this.locationId = locationId;
        return this;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public JobPostRequest setCompanyId(Integer companyId) {
        this.companyId = companyId;
        return this;
    }

    public List<Integer> getSpecializationIds() {
        return specializationIds;
    }

    public JobPostRequest setSpecializationIds(List<Integer> specializationIds) {
        this.specializationIds = specializationIds;
        return this;
    }

    public List<JobPostSkillSetDto> getSkills() {
        return skills;
    }

    public JobPostRequest setSkills(List<JobPostSkillSetDto> skills) {
        this.skills = skills;
        return this;
    }
}
