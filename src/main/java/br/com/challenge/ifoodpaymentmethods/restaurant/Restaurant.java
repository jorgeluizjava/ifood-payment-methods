package br.com.challenge.ifoodpaymentmethods.restaurant;

import br.com.challenge.ifoodpaymentmethods.paymentmethods.PaymentMethod;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "restaurant")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @ManyToMany
    @JoinTable(
            name = "restaurant_payment_methods",
            joinColumns = @JoinColumn(name = "restaurant_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "payment_method_id", referencedColumnName = "id")
    )
    private Set<PaymentMethod> paymentMethods = new HashSet<>();

    @Deprecated
    Restaurant() {
    }

    public Restaurant(@NotNull String name, @NotEmpty Set<PaymentMethod> paymentMethods) {
        Assert.hasText(name, "Name is required");
        Assert.notEmpty(paymentMethods, "paymentMethods can not be empty");
        this.name = name;
        this.paymentMethods = paymentMethods;
    }

    public boolean accept(PaymentMethod paymentMethod) {
        return this.paymentMethods.contains(paymentMethod);
    }
}
