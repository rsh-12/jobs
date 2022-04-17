package ru.rsh12.job.entity;
/*
 * Date: 13.04.2022
 * Time: 7:01 AM
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
public class JobPostSkillSetId implements Serializable {

    @Column(name = "skill_set_id")
    private Integer skillSetId;

    @Column(name = "job_post_id")
    private Integer jobPostId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JobPostSkillSetId that = (JobPostSkillSetId) o;

        if (!skillSetId.equals(that.skillSetId)) {
            return false;
        }
        return jobPostId.equals(that.jobPostId);
    }

    @Override
    public int hashCode() {
        int result = skillSetId.hashCode();
        result = 31 * result + jobPostId.hashCode();
        return result;
    }

}
