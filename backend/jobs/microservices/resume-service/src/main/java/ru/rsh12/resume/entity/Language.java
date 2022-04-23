package ru.rsh12.resume.entity;
/*
 * Date: 21.04.2022
 * Time: 5:36 AM
 * */

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Max(70)
    @NotBlank
    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany
    @ToString.Exclude
    private Set<ResumeLanguage> resumes = new HashSet<>();

    public Language(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Language country = (Language) o;

        if (!Objects.equals(id, country.id)) {
            return false;
        }
        return name.equals(country.name);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + name.hashCode();
        return result;
    }

}
