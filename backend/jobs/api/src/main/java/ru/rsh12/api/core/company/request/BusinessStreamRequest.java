package ru.rsh12.api.core.company.request;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

public class BusinessStreamRequest {

    @NotBlank
    @Size(max = 150, message = "Value must be less than 151 characters")
    private String name;

    @Valid
    private Set<CompanyRequest> companies = new HashSet<>();

    public BusinessStreamRequest() {
    }

    public BusinessStreamRequest(String name) {
        this.name = name;
    }

    public BusinessStreamRequest(String name, Set<CompanyRequest> companies) {
        this.name = name;
        this.companies = companies;
    }

    public String getName() {
        return name;
    }

    public BusinessStreamRequest setName(String name) {
        this.name = name;
        return this;
    }

    public Set<CompanyRequest> getCompanies() {
        return companies;
    }

    public BusinessStreamRequest setCompanies(Set<CompanyRequest> companies) {
        this.companies = companies;
        return this;
    }
}
