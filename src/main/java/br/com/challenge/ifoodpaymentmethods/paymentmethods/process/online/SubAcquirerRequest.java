package br.com.challenge.ifoodpaymentmethods.paymentmethods.process.online;

import br.com.challenge.ifoodpaymentmethods.paymentmethods.PaymentMethod;
import br.com.challenge.ifoodpaymentmethods.paymentmethods.process.Payment;
import br.com.challenge.ifoodpaymentmethods.paymentmethods.process.online.creditcard.CreditCardInformation;
import br.com.challenge.ifoodpaymentmethods.paymentmethods.process.online.creditcard.SubAcquirerCreditCard;
import br.com.challenge.ifoodpaymentmethods.user.User;
import org.springframework.util.Assert;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class SubAcquirerRequest {

    @NotBlank
    private String orderId;
    @NotBlank
    private String customerName;
    @NotBlank
    private String customerEmail;
    @NotBlank
    private String paymentType;
    @Min(value =  1)
    private BigDecimal amount;
    @NotNull
    @Min(value = 0)
    private Integer installments;
    @NotNull
    private SubAcquirerCreditCard creditCard;

    @Deprecated
    public SubAcquirerRequest() {
    }

    public SubAcquirerRequest(Payment payment) {

        Assert.notNull(payment, "payment is required");

        User user = payment.getUser();
        PaymentMethod paymentMethod = payment.getPaymentMethod();
        CreditCardInformation creditCardInformation = payment.getCreditCardInformation();

        this.orderId = payment.getOrderId().toString();
        this.customerName = user.getName();
        this.customerEmail = user.getLogin();
        this.paymentType = paymentMethod.getPaymentMethodType().name();
        this.amount = payment.getAmount();
        this.installments = 0;
        this.creditCard = new SubAcquirerCreditCard(creditCardInformation);
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Integer getInstallments() {
        return installments;
    }

    public SubAcquirerCreditCard getCreditCard() {
        return creditCard;
    }
}
