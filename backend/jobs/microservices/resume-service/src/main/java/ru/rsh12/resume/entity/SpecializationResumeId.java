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
public class SpecializationResumeId implements Serializable {

    @Column(name = "specialization_id")
    private Integer specializationId;

    @Column(name = "resume_id")
    private Integer resumeId;

}
