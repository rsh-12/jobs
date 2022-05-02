package ru.rsh12.resume.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Getter
@Setter
@Entity(name = "specialization_resume")
@ToString
@NoArgsConstructor
public class SpecializationResume {

    @EmbeddedId
    private SpecializationResumeId id;

    @NotNull
    @Column(name = "specialization_id", insertable = false, updatable = false, nullable = false)
    private Integer specializationId;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "resume_id", insertable = false, updatable = false, nullable = false)
    private Resume resume;

    public SpecializationResume(Integer specializationId, Resume resume) {
        this.specializationId = specializationId;
        this.resume = resume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SpecializationResume that = (SpecializationResume) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}