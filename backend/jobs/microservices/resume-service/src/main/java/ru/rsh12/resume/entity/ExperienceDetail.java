package ru.rsh12.resume.entity;
/*
 * Date: 23.04.2022
 * Time: 6:06 PM
 * */

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

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

    @Size(max = 50)
    @NotBlank
    @Column(nullable = false)
    private String jobTitle;

    @Size(max = 250)
    private String description;

    @Size(max = 100)
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


}
