package ru.rsh12.company.entity;
/*
 * Date: 04.04.2022
 * Time: 1:20 PM
 * */

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Represents an image of the company as a path or link.
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
@Entity
public class CompanyImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(unique = true)
    @Size(max = 250, message = "Value must be less than 251 characters")
    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Company company;

    public CompanyImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompanyImage that = (CompanyImage) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
