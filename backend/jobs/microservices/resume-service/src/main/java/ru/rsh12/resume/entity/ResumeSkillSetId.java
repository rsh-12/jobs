package ru.rsh12.resume.entity;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Setter
@Getter
@ToString
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ResumeSkillSetId implements Serializable {

    @Column(name = "skill_set_id")
    private Integer skillSetId;

    @Column(name = "resume_id")
    private Integer resumeId;

}
