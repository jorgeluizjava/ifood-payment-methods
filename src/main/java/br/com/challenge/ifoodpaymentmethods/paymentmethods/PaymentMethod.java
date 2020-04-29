package br.com.challenge.ifoodpaymentmethods.paymentmethods;

import org.springframework.util.Assert;

import javax.persistence.*;

@Entity
@Table(name = "payment_method")
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean online;
    private String description;
    @Enumerated(EnumType.STRING)
    private PaymentMethodType paymentMethodType;

    @Deprecated
    PaymentMethod() {
    }

    public PaymentMethod(boolean online, String description, PaymentMethodType paymentMethodType) {

        Assert.hasText(description, "description is required");
        Assert.notNull(paymentMethodType, "paymentMethodType is required");

        this.online = online;
        this.description = description;
        this.paymentMethodType = paymentMethodType;
    }

    public Long getId() {
        return id;
    }

    public boolean isOnline() {
        return online;
    }

    public String getDescription() {
        return description;
    }

    public PaymentMethodType getPaymentMethodType() {
        return paymentMethodType;
    }

    @Override
    public String toString() {
        return "PaymentMethod{" +
                "id=" + id +
                ", online=" + online +
                ", description='" + description + '\'' +
                ", paymentMethodType=" + paymentMethodType +
                '}';
    }
}
