package br.com.challenge.ifoodpaymentmethods.paymentmethods;

import org.springframework.util.Assert;

public class PaymentMethodDTO {

    private Long paymentMethodId;
    private String description;

    public PaymentMethodDTO(PaymentMethod paymentMethod) {
        Assert.notNull(paymentMethod, "paymentMethod cannot be null");
        this.paymentMethodId = paymentMethod.getId();
        this.description = paymentMethod.getDescription();
    }

    public Long getPaymentMethodId() {
        return paymentMethodId;
    }

    public String getDescription() {
        return description;
    }


    @Override
    public String toString() {
        return "PaymentMethodsDTO{" +
                "paymentMethodId=" + paymentMethodId +
                ", description='" + description + '\'' +
                '}';
    }
}
