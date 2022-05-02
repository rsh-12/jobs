package ru.rsh12.company.entity;
/*
 * Date: 03.04.2022
 * Time: 4:23 PM
 * */

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @OneToMany(mappedBy = "businessStream")
    @ToString.Exclude
    private List<Company> companies = new ArrayList<>();

    public BusinessStream(String name) {
        this.name = name;
    }

    public boolean addCompany(Company company) {
        if (company == null) {
            return false;
        }

        company.setBusinessStream(this);
        return this.companies.add(company);
    }

    public boolean removeCompany(Company company) {
        if (company == null) {
            return false;
        }

        company.setBusinessStream(null);
        return this.companies.remove(company);
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BusinessStream that = (BusinessStream) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}

