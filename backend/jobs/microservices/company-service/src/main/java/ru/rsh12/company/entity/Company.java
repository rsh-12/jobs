package ru.rsh12.company.entity;
/*
 * Date: 04.04.2022
 * Time: 9:59 AM
 * */

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * Represents the company.
 */
@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @NotBlank
    @Size(max = 100, message = "Value must be less than 101 characters")
    private String name;

    @Lob
    private String description;

    @PastOrPresent
    private LocalDate establishmentDate;

    @Size(max = 250, message = "Value must be less than 251 characters")
    private String websiteUrl;

    @ManyToOne
    private BusinessStream businessStream;

    @OneToMany
    @ToString.Exclude
    private List<CompanyImage> images;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    public void addImage(CompanyImage image) {
        if (image != null) {
            this.images.add(image);
            image.setCompany(this);
        }
    }

    public void removeImage(CompanyImage image) {
        if (image != null) {
            this.images.remove(image);
            image.setCompany(null);
        }
    }

    public void setImages(List<CompanyImage> images) {
        if (images != null) {
            images.forEach(image -> image.setCompany(this));

            this.images.forEach(image -> image.setCompany(null));
            this.images = images;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Company company = (Company) o;

        if (!Objects.equals(id, company.id)) {
            return false;
        }
        if (!name.equals(company.name)) {
            return false;
        }
        return Objects.equals(establishmentDate, company.establishmentDate);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + name.hashCode();
        return result;
    }

}
