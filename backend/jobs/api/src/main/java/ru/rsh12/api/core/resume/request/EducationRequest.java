package ru.rsh12.api.core.resume.request;

import java.time.LocalDate;

public class EducationRequest {

    private String institutionName;
        private String faculty;
    private LocalDate startingDate;
    private LocalDate completionDate;

    public EducationRequest(String institutionName,
                            String faculty,
                            LocalDate startingDate,
                            LocalDate completionDate) {
        this.institutionName = institutionName;
        this.faculty = faculty;
        this.startingDate = startingDate;
        this.completionDate = completionDate;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public EducationRequest setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
        return this;
    }

    public String getFaculty() {
        return faculty;
    }

    public EducationRequest setFaculty(String faculty) {
        this.faculty = faculty;
        return this;
    }

    public LocalDate getStartingDate() {
        return startingDate;
    }

    public EducationRequest setStartingDate(LocalDate startingDate) {
        this.startingDate = startingDate;
        return this;
    }

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    public EducationRequest setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
        return this;
    }
}
