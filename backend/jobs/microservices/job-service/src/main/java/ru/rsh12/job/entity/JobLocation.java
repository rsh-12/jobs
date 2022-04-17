package ru.rsh12.job.entity;
/*
 * Date: 13.04.2022
 * Time: 6:26 AM
 * */

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
public class JobLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Max(100)
    @NotBlank
    private String street;

    @Max(100)
    @NotBlank
    private String city;

    @Max(100)
    @NotBlank
    private String state;

    @Max(100)
    @NotBlank
    private String country;

    @OneToMany
    @ToString.Exclude
    private List<JobPost> jobs = new ArrayList<>();

    public JobLocation(String street, String city, String state, String country) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.country = country;
    }

    public boolean addJobPost(JobPost jobPost) {
        if (jobPost == null) {
            return false;
        }

        jobPost.setLocation(this);
        return this.jobs.add(jobPost);
    }

    public boolean removeJobPost(JobPost jobPost) {
        if (jobPost == null) {
            return false;
        }

        jobPost.setLocation(null);
        return this.jobs.remove(jobPost);
    }

    public void setJobs(List<JobPost> jobs) {
        if (jobs != null) {
            jobs.forEach(job -> job.setLocation(this));

            this.jobs.forEach(job -> job.setLocation(null));
            this.jobs = jobs;
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

        JobLocation that = (JobLocation) o;

        if (!Objects.equals(id, that.id)) {
            return false;
        }
        if (!Objects.equals(street, that.street)) {
            return false;
        }
        if (!Objects.equals(city, that.city)) {
            return false;
        }
        if (!Objects.equals(state, that.state)) {
            return false;
        }
        return Objects.equals(country, that.country);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        return result;
    }

}
