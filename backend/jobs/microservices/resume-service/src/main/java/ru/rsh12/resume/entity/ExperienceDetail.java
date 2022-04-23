package ru.rsh12.resume.entity;
/*
 * Date: 23.04.2022
 * Time: 6:06 PM
 * */

import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
public class ExperienceDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private boolean isCurrentJob = false;

    private LocalDate startDate;

    private LocalDate endDate;

    @Max(value = 50)
    @NotBlank
    @Column(nullable = false)
    private String jobTitle;

    @Max(value = 250)
    private String description;

    @Max(value = 100)
    private String companyName;

    @ManyToOne
    private Resume resume;

    public ExperienceDetail(
            boolean isCurrentJob,
            LocalDate startDate,
            LocalDate endDate,
            String jobTitle,
            String description,
            String companyName,
            Resume resume) {
        this.isCurrentJob = isCurrentJob;
        this.startDate = startDate;
        this.endDate = endDate;
        this.jobTitle = jobTitle;
        this.description = description;
        this.companyName = companyName;
        this.resume = resume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ExperienceDetail that = (ExperienceDetail) o;

        if (!Objects.equals(id, that.id)) {
            return false;
        }
        if (!Objects.equals(startDate, that.startDate)) {
            return false;
        }
        if (!Objects.equals(endDate, that.endDate)) {
            return false;
        }
        if (!jobTitle.equals(that.jobTitle)) {
            return false;
        }
        return Objects.equals(companyName, that.companyName);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + jobTitle.hashCode();
        return result;
    }

}
