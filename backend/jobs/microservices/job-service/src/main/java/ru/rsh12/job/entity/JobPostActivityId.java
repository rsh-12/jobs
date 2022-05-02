package ru.rsh12.job.entity;
/*
 * Date: 13.04.2022
 * Time: 7:12 AM
 * */

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@ToString
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class JobPostActivityId implements Serializable {

    @Column(name = "job_post_id")
    private Integer jobPostId;

    @Column(name = "resume_id")
    private Integer resumeId;

}
