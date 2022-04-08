package ru.rsh12.company.entity;
/*
 * Date: 03.04.2022
 * Time: 4:23 PM
 * */

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents the company's industry.
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
@Entity
public class BusinessStream {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size(max = 150, message = "Value must be less than 151 characters")
    private String name;

    @OneToMany
    @ToString.Exclude
    private List<Company> companies = new ArrayList<>();

    @PreRemove
    private void preRemove() {
        this.companies.forEach(company -> company.setBusinessStream(null));
    }

    public BusinessStream(String name) {
        this.name = name;
    }

    public void addCompany(Company company) {
        if (company != null) {
            this.companies.add(company);
            company.setBusinessStream(this);
        }
    }

    public void removeCompany(Company company) {
        if (company != null) {
            this.companies.remove(company);
            company.setBusinessStream(null);
        }
    }

    public void setCompanies(List<Company> companies) {
        if (companies != null) {
            companies.forEach(company -> company.setBusinessStream(this));

            this.companies.forEach(company -> company.setBusinessStream(null));
            this.companies = companies;
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

        BusinessStream that = (BusinessStream) o;

        if (!Objects.equals(id, that.id)) {
            return false;
        }
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + name.hashCode();
        return result;
    }

}

