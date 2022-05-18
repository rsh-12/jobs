package ru.rsh12.resume.entity;
/*
 * Date: 19.04.2022
 * Time: 9:33 AM
 * */

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 70)
    @NotBlank
    private String desiredJobPosition;

    @Size(max = 250)
    private String description;

    @Min(0)
    private Integer salary;

    @Size(min = 3, max = 3)
    private String currency = "RUB";

    @NotBlank
    @Size(max = 100)
    private String accountId; // from auth server

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    @ManyToMany
    @ToString.Exclude
    @JoinTable(
            name = "citizenship",
            joinColumns = @JoinColumn(name = "resume_id"),
            inverseJoinColumns = @JoinColumn(name = "country_id"))
    private List<Country> citizenship = new ArrayList<>();

    @OneToMany
    @ToString.Exclude
    @JoinColumn(name = "resume_id")
    private List<ResumeSkillSet> skills = new ArrayList<>();

    @OneToMany
    @ToString.Exclude
    @JoinColumn(name = "resume_id")
    private List<ResumeLanguage> languages = new ArrayList<>();

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<EducationDetail> educationDetails = new ArrayList<>();

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<ExperienceDetail> experienceDetails = new ArrayList<>();

    @OneToMany(mappedBy = "resume")
    @ToString.Exclude
    private List<SpecializationResume> specializations = new ArrayList<>();

    public Resume(
            String desiredJobPosition,
            String description,
            Integer salary,
            String currency,
            String accountId) {
        this.desiredJobPosition = desiredJobPosition;
        this.description = description;
        this.salary = salary;
        this.currency = (currency == null || currency.length() != 3) ? "RUB" : currency;
        this.accountId = accountId;
    }

    public void setSkills(List<ResumeSkillSet> skills) {
        if (skills != null) {
            skills.forEach(resumeSkillSet -> resumeSkillSet.setResume(this));

            this.skills.forEach(resumeSkillSet -> resumeSkillSet.setResume(null));
            this.skills = skills;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        return Objects.equals(id, resume.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
