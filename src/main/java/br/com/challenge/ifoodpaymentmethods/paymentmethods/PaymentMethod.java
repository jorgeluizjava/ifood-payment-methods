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
    @Enumerated(EnumType.STRING)
    private PaymentMethodType paymentMethodType;

    @Deprecated
    PaymentMethod() {
    }

    public PaymentMethod(@NotBlank String description, @NotNull PaymentMethodType paymentMethodType) {

        Assert.hasText(description, "description is required");
        Assert.notNull(paymentMethodType, "paymentMethodType is required");

        this.description = description;
        this.paymentMethodType = paymentMethodType;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public boolean isOnline() {
        return paymentMethodType.isOnline();
    }

    public PaymentMethodType getPaymentMethodType() {
        return paymentMethodType;
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
