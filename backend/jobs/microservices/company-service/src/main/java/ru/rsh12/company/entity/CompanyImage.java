package ru.rsh12.company.entity;
/*
 * Date: 04.04.2022
 * Time: 1:20 PM
 * */

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
    private String image;

    @ManyToOne
    private Company company;

    public CompanyImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CompanyImage that = (CompanyImage) o;

        if (!Objects.equals(id, that.id)) {
            return false;
        }
        return image.equals(that.image);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + image.hashCode();
        return result;
    }

}
