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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SpecializationResume that = (SpecializationResume) o;

        if (!Objects.equals(id, that.id)) {
            return false;
        }
        if (!specializationId.equals(that.specializationId)) {
            return false;
        }
        return resume.equals(that.resume);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + specializationId.hashCode();
        result = 31 * result + resume.hashCode();
        return result;
    }

}