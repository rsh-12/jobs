package ru.rsh12.company.entity;
/*
 * Date: 04.04.2022
 * Time: 9:59 AM
 * */

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents the company.
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size(max = 100, message = "Value must be less than 101 characters")
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @PastOrPresent
    private LocalDate establishmentDate;

    @Size(max = 250, message = "Value must be less than 251 characters")
    private String websiteUrl;

    @ManyToOne
    private BusinessStream businessStream;

    @OneToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            mappedBy = "company")
    @ToString.Exclude
    private List<CompanyImage> images = new ArrayList<>();

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    public Company(
            @NonNull String name,
            String description,
            LocalDate establishmentDate,
            String websiteUrl,
            BusinessStream businessStream,
            List<CompanyImage> images) {
        this.name = name;
        this.description = description;
        this.establishmentDate = establishmentDate;
        this.websiteUrl = websiteUrl;
        this.businessStream = businessStream;
        this.images = images;
    }

    public boolean addImage(CompanyImage image) {
        if (image == null) {
            return false;
        }

        image.setCompany(this);
        return this.images.add(image);
    }

    public boolean removeImage(CompanyImage image) {
        if (image == null) {
            return false;
        }

        image.setCompany(null);
        return this.images.remove(image);
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        return Objects.equals(id, company.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
