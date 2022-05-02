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
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Entity(name = "resume_skill_set")
@ToString
@NoArgsConstructor
public class ResumeSkillSet {

    @EmbeddedId
    private ResumeSkillSetId id;

    @Max(50)
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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ResumeSkillSet that = (ResumeSkillSet) o;

        if (!level.equals(that.level)) {
            return false;
        }
        if (!skill.equals(that.skill)) {
            return false;
        }
        return resume.equals(that.resume);
    }

    @Override
    public int hashCode() {
        int result = level.hashCode();
        result = 31 * result + skill.hashCode();
        result = 31 * result + resume.hashCode();
        return result;
    }

}
