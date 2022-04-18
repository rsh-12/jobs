package ru.rsh12.job.entity;
/*
 * Date: 12.04.2022
 * Time: 10:56 PM
 * */

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
public class JobType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Max(value = 100, message = "Value must be less than 101 characters")
    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany
    @ToString.Exclude
    @JoinColumn(name = "job_type_id")
    private Set<JobPost> jobs = new HashSet<>();

    public JobType(String name) {
        this.name = name;
    }

    public boolean addJobPost(JobPost jobPost) {
        if (jobPost == null) {
            return false;
        }

        jobPost.setType(this);
        return jobs.add(jobPost);
    }

    public void setJobs(Set<JobPost> jobs) {
        if (jobs != null) {
            jobs.forEach(job -> job.setType(this));

            this.jobs.forEach(job -> job.setType(null));
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

        JobType jobType = (JobType) o;

        if (!Objects.equals(id, jobType.id)) {
            return false;
        }
        return name.equals(jobType.name);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + name.hashCode();
        return result;
    }

}
