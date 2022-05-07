package ru.rsh12.api.core.company.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CreateCompanyRequest {

    @NotBlank
    @Size(max = 100, message = "Value must be less than 101 characters")
    private String name;

    private String description;

    @PastOrPresent
    private LocalDate establishmentDate;

    @Size(max = 250, message = "Value must be less than 251 characters")
    private String websiteUrl;

    private List<String> images = new ArrayList<>();

    public CreateCompanyRequest() {
    }

    public CreateCompanyRequest(String name,
                                String description,
                                LocalDate establishmentDate,
                                String websiteUrl,
                                List<String> images) {
        this.name = name;
        this.description = description;
        this.establishmentDate = establishmentDate;
        this.websiteUrl = websiteUrl;
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public CreateCompanyRequest setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CreateCompanyRequest setDescription(String description) {
        this.description = description;
        return this;
    }

    public LocalDate getEstablishmentDate() {
        return establishmentDate;
    }

    public CreateCompanyRequest setEstablishmentDate(LocalDate establishmentDate) {
        this.establishmentDate = establishmentDate;
        return this;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public CreateCompanyRequest setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
        return this;
    }

    public List<String> getImages() {
        return images;
    }

    public CreateCompanyRequest setImages(List<String> images) {
        this.images = images;
        return this;
    }
}
