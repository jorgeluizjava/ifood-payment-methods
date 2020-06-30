package br.com.challenge.ifoodpaymentmethods.user;

import br.com.challenge.ifoodpaymentmethods.paymentmethods.PaymentMethod;
import br.com.challenge.ifoodpaymentmethods.restaurant.Restaurant;
import br.com.challenge.ifoodpaymentmethods.shared.fraudster.PaymentMethodFraudsterVerifier;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @NotBlank
    @Column(unique = true)
    private String login;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "user_desired_payment_methods",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "payment_method_id")
    )
    private Set<PaymentMethod> desiredPaymentMethods = new HashSet<>();

    @Deprecated
    User() {
    }

    public User(@NotBlank @Email String login, String name, @NotEmpty Set<PaymentMethod> desiredPaymentMethods) {
        Assert.hasText(login, "login is required");
        Assert.hasText(name, "name is required");
        Assert.notEmpty(desiredPaymentMethods, "desiredPaymentMethods is required");
        this.login = login;
        this.name = name;
        this.desiredPaymentMethods = desiredPaymentMethods;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public Set<PaymentMethod> filterDesiredPaymentMethods(Restaurant restaurant, PaymentMethodFraudsterVerifier paymentMethodFraudsterVerifier) {

        Assert.notNull(restaurant, "restaurant must not be null");
        Assert.notNull(paymentMethodFraudsterVerifier, "PaymentMethodFraudsterVerifier must not be null");

        return desiredPaymentMethods
                    .stream()
                    .filter(paymentMethod -> restaurant.accept(paymentMethod))
                    .filter(paymentMethod -> paymentMethodFraudsterVerifier.verify(this, paymentMethod))
                    .collect(toSet());
    }
}
