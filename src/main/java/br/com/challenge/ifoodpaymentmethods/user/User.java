package br.com.challenge.ifoodpaymentmethods.user;

import br.com.challenge.ifoodpaymentmethods.paymentmethods.PaymentMethod;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usuario")
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

    public boolean accept(PaymentMethod paymentMethod) {
        return desiredPaymentMethods.contains(paymentMethod);
    }
}
