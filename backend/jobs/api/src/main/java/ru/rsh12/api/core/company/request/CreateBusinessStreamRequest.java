package ru.rsh12.api.core.company.request;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

public class CreateBusinessStreamRequest {

    @NotBlank
    @Size(max = 150, message = "Value must be less than 151 characters")
    private String name;

    @Valid
    private Set<CreateCompanyRequest> companies = new HashSet<>();

    public CreateBusinessStreamRequest() {
    }

    public CreateBusinessStreamRequest(String name) {
        this.name = name;
    }

    public CreateBusinessStreamRequest(String name, Set<CreateCompanyRequest> companies) {
        this.name = name;
        this.companies = companies;
    }

    public String getName() {
        return name;
    }

    public CreateBusinessStreamRequest setName(String name) {
        this.name = name;
        return this;
    }

    public Set<CreateCompanyRequest> getCompanies() {
        return companies;
    }

    public CreateBusinessStreamRequest setCompanies(Set<CreateCompanyRequest> companies) {
        this.companies = companies;
        return this;
    }
}
