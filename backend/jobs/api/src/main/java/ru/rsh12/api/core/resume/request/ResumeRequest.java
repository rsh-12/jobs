package ru.rsh12.api.core.resume.request;

import ru.rsh12.api.core.resume.dto.SkillSetDto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class ResumeRequest {

    @Size(max = 70)
    @NotBlank
    private String desiredJobPosition;

    @Size(max = 250)
    private String description;

    @Min(0)
    private Integer salary;

    @Size(min = 3, max = 3)
    private String currency = "RUB";

    private List<Integer> countryIds = new ArrayList<>();

    private List<SkillSetDto> skills = new ArrayList<>();

    private List<LanguageRequest> languages = new ArrayList<>();

    private List<EducationRequest> education = new ArrayList<>();

    private List<ExperienceRequest> experience = new ArrayList<>();

    private List<Integer> specializationIds = new ArrayList<>();

    public String getDesiredJobPosition() {
        return desiredJobPosition;
    }

    public void setDesiredJobPosition(String desiredJobPosition) {
        this.desiredJobPosition = desiredJobPosition;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<Integer> getCountryIds() {
        return countryIds;
    }

    public void setCountryIds(List<Integer> countryIds) {
        this.countryIds = countryIds;
    }

    public List<SkillSetDto> getSkills() {
        return skills;
    }

    public void setSkills(List<SkillSetDto> skills) {
        this.skills = skills;
    }

    public List<LanguageRequest> getLanguages() {
        return languages;
    }

    public ResumeRequest setLanguages(List<LanguageRequest> languages) {
        this.languages = languages;
        return this;
    }

    public List<EducationRequest> getEducation() {
        return education;
    }

    public ResumeRequest setEducation(List<EducationRequest> education) {
        this.education = education;
        return this;
    }

    public List<ExperienceRequest> getExperience() {
        return experience;
    }

    public ResumeRequest setExperience(List<ExperienceRequest> experience) {
        this.experience = experience;
        return this;
    }

    public List<Integer> getSpecializationIds() {
        return specializationIds;
    }

    public ResumeRequest setSpecializationIds(List<Integer> specializationIds) {
        this.specializationIds = specializationIds;
        return this;
    }
}
