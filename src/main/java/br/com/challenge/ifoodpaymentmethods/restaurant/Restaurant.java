package br.com.challenge.ifoodpaymentmethods.restaurant;

import br.com.challenge.ifoodpaymentmethods.paymentmethods.PaymentMethod;
import br.com.challenge.ifoodpaymentmethods.user.User;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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

    public Set<PaymentMethod> getPaymentMethods() {
        return Collections.unmodifiableSet(paymentMethods);
    }

    public Set<PaymentMethod> filterDesiredPaymentMethods(User user) {
        Assert.notNull(user, "user must not be null");
        return this.paymentMethods
                    .stream()
                        .filter(paymentMethod -> user.accept(paymentMethod))
                    .collect(Collectors.toSet());
    }
}
