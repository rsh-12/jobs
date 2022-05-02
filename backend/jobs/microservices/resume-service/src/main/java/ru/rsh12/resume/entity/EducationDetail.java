package ru.rsh12.resume.entity;
/*
 * Date: 21.04.2022
 * Time: 5:58 AM
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
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
public class EducationDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Max(150)
    @NotBlank
    @Column(nullable = false)
    private String institutionName;

    @Max(150)
    private String faculty;

    @PastOrPresent
    private LocalDate startingDate;

    private LocalDate completionDate;

    @ManyToOne
    private Resume resume;

    public EducationDetail(
            String institutionName,
            String faculty,
            LocalDate startingDate,
            LocalDate completionDate,
            Resume resume) {
        this.institutionName = institutionName;
        this.faculty = faculty;
        this.startingDate = startingDate;
        this.completionDate = completionDate;
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

        EducationDetail that = (EducationDetail) o;

        if (!Objects.equals(id, that.id)) {
            return false;
        }
        return resume.equals(that.resume);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + resume.hashCode();
        return result;
    }

}
