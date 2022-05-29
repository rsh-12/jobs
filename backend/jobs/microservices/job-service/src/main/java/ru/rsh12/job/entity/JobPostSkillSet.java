package ru.rsh12.job.entity;
/*
 * Date: 13.04.2022
 * Time: 7:03 AM
 * */

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Getter
@Setter
@Entity(name = "job_post_skill_set")
@ToString
@NoArgsConstructor
public class JobPostSkillSet {

    @EmbeddedId
    private JobPostSkillSetId id;

    @Size(max = 50)
    @NotBlank
    @Column(nullable = false)
    private String level;

    @NotNull
    @Column(name = "skill_set_id", insertable = false, updatable = false)
    private Integer skillSetId;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "job_post_id", insertable = false, updatable = false)
    private JobPost jobPost;

    public JobPostSkillSet(String level, Integer skillSetId, JobPost jobPost) {
        this.level = level;
        this.skillSetId = skillSetId;
        this.jobPost = jobPost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JobPostSkillSet that = (JobPostSkillSet) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
