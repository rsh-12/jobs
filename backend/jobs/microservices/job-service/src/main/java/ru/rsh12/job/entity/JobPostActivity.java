package ru.rsh12.job.entity;
/*
 * Date: 13.04.2022
 * Time: 7:12 AM
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
import java.util.Objects;

@Getter
@Setter
@Entity(name = "job_post_activity")
@ToString
@NoArgsConstructor
public class JobPostActivity {

    @EmbeddedId
    private JobPostActivityId id;

    @NotBlank
    @Column(nullable = false)
    private String status = "pending";

    @ManyToOne
    @JoinColumn(name = "job_post_id", insertable = false, updatable = false)
    private JobPost jobPost;

    @Column(name = "resume_id", insertable = false, updatable = false)
    private Integer resumeId;

    public JobPostActivity(String status, JobPost jobPost, int resumeId) {
        this.status = status;
        this.jobPost = jobPost;
        this.resumeId = resumeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JobPostActivity that = (JobPostActivity) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
