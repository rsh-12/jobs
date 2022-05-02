package ru.rsh12.resume.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Entity(name = "resume_language")
@NoArgsConstructor
public class ResumeLanguage {

    @EmbeddedId
    private ResumeLanguageId id;

    @Max(50)
    @NotBlank
    private String level;

    @ManyToOne
    @JoinColumn(name = "resume_id", insertable = false, updatable = false)
    private Resume resume;

    @ManyToOne
    @JoinColumn(name = "language_id", insertable = false, updatable = false)
    private Language language;

    public ResumeLanguage(String level, Resume resume, Language language) {
        this.level = level;
        this.resume = resume;
        this.language = language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ResumeLanguage that = (ResumeLanguage) o;

        if (!level.equals(that.level)) {
            return false;
        }
        if (!resume.equals(that.resume)) {
            return false;
        }
        return language.equals(that.language);
    }

    @Override
    public int hashCode() {
        int result = level.hashCode();
        result = 31 * result + resume.hashCode();
        result = 31 * result + language.hashCode();
        return result;
    }

}
