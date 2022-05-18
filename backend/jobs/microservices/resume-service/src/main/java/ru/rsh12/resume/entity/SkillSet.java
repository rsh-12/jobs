package ru.rsh12.resume.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
public class SkillSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Max(50)
    @NotBlank
    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany
    @ToString.Exclude
    private List<ResumeSkillSet> resumes = new ArrayList<>();

    public SkillSet(Integer id) {
        this.id = id;
    }

    public SkillSet(String name) {
        this.name = name;
    }

    public SkillSet(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SkillSet skillSet = (SkillSet) o;

        return Objects.equals(id, skillSet.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

}
