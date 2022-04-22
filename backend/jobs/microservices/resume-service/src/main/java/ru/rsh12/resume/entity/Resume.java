package ru.rsh12.resume.entity;
/*
 * Date: 19.04.2022
 * Time: 9:33 AM
 * */

import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Max(70)
    @NotBlank
    private String desiredJobPosition;

    @Max(value = 250)
    private String description;

    @Min(0)
    private Integer salary;

    @Size(min = 3, max = 3)
    private String currency = "RUB";

    @NotBlank
    @Size(max = 100)
    private String accountId; // from auth server

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    public Resume(
            String desiredJobPosition,
            String description,
            Integer salary,
            String currency,
            String accountId) {
        this.desiredJobPosition = desiredJobPosition;
        this.description = description;
        this.salary = salary;
        this.currency = (currency == null || currency.length() != 3) ? "RUB" : currency;
        this.accountId = accountId;
    }

}
