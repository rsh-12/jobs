package ru.rsh12.job.entity;
/*
 * Date: 13.04.2022
 * Time: 6:28 AM
 * */

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Pattern.Flag;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
public class JobPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private boolean isActive = true;

    @Min(0)
    private Integer salarayFrom;

    @Min(0)
    private Integer salarayUpTo;

    @Size(min = 3, max = 3)
    private String curreny = "RUB";

    @Max(50)
    @Pattern(
            flags = {Flag.CASE_INSENSITIVE},
            regexp = ".+@.+\\.+\\w{2,8}",
            message = "Invalid email address format")
    private String email;

    @Max(50)
    @Pattern(
            regexp = "\\+?\\d([\\s-]?\\d{3}){0,2}([\\s-]?\\d{2}){2}",
            message = "Invalid phone number format")
    private String phone;

    @ManyToOne
    @ToString.Exclude
    private JobType type;

    @ManyToOne
    @ToString.Exclude
    private JobLocation location;

    @NotNull
    private Integer postedById;

    @ManyToMany
    @ToString.Exclude
    private List<Specialization> specializations = new ArrayList<>();

    @OneToMany
    @ToString.Exclude
    private List<JobPostSkillSet> skills = new ArrayList<>();

    @OneToMany
    @ToString.Exclude
    private List<JobPostActivity> activities = new ArrayList<>();

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        JobPost jobPost = (JobPost) o;
        return id != null && Objects.equals(id, jobPost.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
