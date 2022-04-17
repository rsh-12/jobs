package ru.rsh12.job.entity;
/*
 * Date: 13.04.2022
 * Time: 7:12 AM
 * */

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class JobPostActivityId implements Serializable {

    @Column(name = "job_post_id")
    private Integer jobPostId;

    @Column(name = "resume_id")
    private Integer resumeId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JobPostActivityId that = (JobPostActivityId) o;

        if (!jobPostId.equals(that.jobPostId)) {
            return false;
        }
        return resumeId.equals(that.resumeId);
    }

    @Override
    public int hashCode() {
        int result = jobPostId.hashCode();
        result = 31 * result + resumeId.hashCode();
        return result;
    }

}
