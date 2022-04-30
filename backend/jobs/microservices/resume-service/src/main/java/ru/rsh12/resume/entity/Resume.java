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

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Max(70)
    @NotBlank
    private String desiredJobPosition;

    @Max(value = 250)
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
    private Set<Country> citizenship = new HashSet<>();

    @OneToMany
    @ToString.Exclude
    private Set<ResumeSkillSet> skills = new HashSet<>();

    @OneToMany
    @ToString.Exclude
    private Set<ResumeLanguage> languages = new HashSet<>();

    @OneToMany
    @ToString.Exclude
    private Set<EducationDetail> educationDetails = new HashSet<>();

    @OneToMany
    @ToString.Exclude
    private Set<ExperienceDetail> experienceDetails = new HashSet<>();

    @OneToMany
    @ToString.Exclude
    private Set<SpecializationResume> specializations = new HashSet<>();

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Resume resume = (Resume) o;

        if (!Objects.equals(id, resume.id)) {
            return false;
        }
        if (!desiredJobPosition.equals(resume.desiredJobPosition)) {
            return false;
        }
        if (!Objects.equals(description, resume.description)) {
            return false;
        }
        if (!Objects.equals(salary, resume.salary)) {
            return false;
        }
        if (!currency.equals(resume.currency)) {
            return false;
        }
        return accountId.equals(resume.accountId);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + desiredJobPosition.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (salary != null ? salary.hashCode() : 0);
        result = 31 * result + currency.hashCode();
        result = 31 * result + accountId.hashCode();
        return result;
    }

}
