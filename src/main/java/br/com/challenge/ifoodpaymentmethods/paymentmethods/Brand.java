package br.com.challenge.ifoodpaymentmethods.paymentmethods;

import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "brand")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String description;

    @Deprecated
    Brand() {
    }

    public Brand(@NotBlank String description) {
        Assert.hasText(description, "description is required");
        this.description = description;
    }

}
