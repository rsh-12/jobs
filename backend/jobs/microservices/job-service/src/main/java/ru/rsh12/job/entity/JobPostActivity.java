package ru.rsh12.job.entity;
/*
 * Date: 13.04.2022
 * Time: 7:12 AM
 * */

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JobPostActivity that = (JobPostActivity) o;

        if (!Objects.equals(resumeId, that.resumeId)) {
            return false;
        }
        if (!Objects.equals(id, that.id)) {
            return false;
        }
        if (!status.equals(that.status)) {
            return false;
        }
        return jobPost.equals(that.jobPost);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + status.hashCode();
        result = 31 * result + jobPost.hashCode();
        result = 31 * result + resumeId.hashCode();
        return result;
    }

}
