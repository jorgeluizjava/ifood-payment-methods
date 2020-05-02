package br.com.challenge.ifoodpaymentmethods.paymentmethods;

import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "payment_method")
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, unique = true)
    private String description;

    @NotNull
    @ManyToOne
    private Brand brand;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PaymentMethodType paymentMethodType;

    @Deprecated
    PaymentMethod() {
    }

    public PaymentMethod(@NotBlank String description, @NotNull PaymentMethodType paymentMethodType, @NotNull Brand brand) {

        Assert.hasText(description, "description is required");
        Assert.notNull(paymentMethodType, "paymentMethodType is required");
        Assert.notNull(brand, "brand is required");

        this.description = description;
        this.paymentMethodType = paymentMethodType;
        this.brand = brand;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentMethod that = (PaymentMethod) o;
        return description.equals(that.description) &&
                paymentMethodType == that.paymentMethodType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, paymentMethodType);
    }
}
