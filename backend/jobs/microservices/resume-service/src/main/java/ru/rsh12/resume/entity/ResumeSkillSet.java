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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

@Getter
@Setter
@Entity(name = "resume_skill_set")
@ToString
@NoArgsConstructor
public class ResumeSkillSet {

    @EmbeddedId
    private ResumeSkillSetId id;

    @Size(max = 50)
    @NotBlank
    @Column(nullable = false)
    private String level;

    @ManyToOne
    @JoinColumn(name = "skill_set_id", insertable = false, updatable = false)
    private SkillSet skill;

    @ManyToOne
    @JoinColumn(name = "resume_id", insertable = false, updatable = false)
    private Resume resume;

    public ResumeSkillSet(String level, SkillSet skill, Resume resume) {
        this.level = level;
        this.skill = skill;
        this.resume = resume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResumeSkillSet that = (ResumeSkillSet) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
