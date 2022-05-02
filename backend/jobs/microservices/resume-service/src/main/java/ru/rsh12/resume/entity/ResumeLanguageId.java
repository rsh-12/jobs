package ru.rsh12.resume.entity;

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
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ResumeLanguageId implements Serializable {

    @Column(name = "resume_id")
    private Integer resumeId;

    @Column(name = "language_id")
    private Integer languageId;

}