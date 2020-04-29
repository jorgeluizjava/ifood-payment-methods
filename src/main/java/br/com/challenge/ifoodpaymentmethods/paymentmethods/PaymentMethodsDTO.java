package br.com.challenge.ifoodpaymentmethods.paymentmethods;

import org.springframework.util.Assert;

public class PaymentMethodsDTO {

    private Long paymentMethodId;
    private boolean online;
    private String description;
    private PaymentMethodType paymentMethodType;

    public PaymentMethodsDTO(PaymentMethod paymentMethod) {
        Assert.notNull(paymentMethod, "paymentMethod cannot be null");
        this.paymentMethodId = paymentMethod.getId();
        this.online = paymentMethod.isOnline();
        this.description = paymentMethod.getDescription();
        this.paymentMethodType = paymentMethod.getPaymentMethodType();
    }

    public Long getPaymentMethodId() {
        return paymentMethodId;
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
        return "PaymentMethodsDTO{" +
                "paymentMethodId=" + paymentMethodId +
                ", online=" + online +
                ", description='" + description + '\'' +
                '}';
    }
}
