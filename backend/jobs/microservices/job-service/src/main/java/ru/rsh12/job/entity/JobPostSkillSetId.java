package ru.rsh12.job.entity;
/*
 * Date: 13.04.2022
 * Time: 7:01 AM
 * */

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@ToString
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class JobPostSkillSetId implements Serializable {

    @Column(name = "skill_set_id")
    private Integer skillSetId;

    @Column(name = "job_post_id")
    private Integer jobPostId;

}
