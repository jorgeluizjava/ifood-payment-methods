package br.com.challenge.ifoodpaymentmethods.restaurant;

import br.com.challenge.ifoodpaymentmethods.shared.fraudster.FraudsterCheck;
import br.com.challenge.ifoodpaymentmethods.paymentmethods.PaymentMethod;
import br.com.challenge.ifoodpaymentmethods.user.User;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;
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

    public Set<PaymentMethod> filterDesiredPaymentMethods(@NotNull User user, @NotNull @Size(min = 1) List<FraudsterCheck> checkFraudsters) {

        Assert.notNull(user, "user must not be null");
        Assert.notNull(checkFraudsters, "checkFraudsters must not be null");
        Assert.isTrue(!checkFraudsters.isEmpty(), "checkFraudsters must not be empty");

        boolean isFraudster = checkFraudsters.stream().anyMatch(fraudsterCheck -> fraudsterCheck.check(user));
        if (isFraudster) {
            return paymentMethods.stream().filter(paymentMethod -> !paymentMethod.isOnline()).collect(Collectors.toSet());
        }

        return this.paymentMethods
                    .stream()
                        .filter(paymentMethod -> user.accept(paymentMethod))
                    .collect(Collectors.toSet());
    }
}
