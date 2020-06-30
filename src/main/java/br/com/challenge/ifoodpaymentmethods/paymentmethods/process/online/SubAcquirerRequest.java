package br.com.challenge.ifoodpaymentmethods.paymentmethods.process.online;

import br.com.challenge.ifoodpaymentmethods.paymentmethods.process.PaymentRequest;
import br.com.challenge.ifoodpaymentmethods.paymentmethods.process.online.creditcard.SubAcquirerCreditCard;
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

    public SubAcquirerRequest(PaymentRequest paymentRequest) {

        Assert.notNull(paymentRequest, "payment is required");

        this.orderId = paymentRequest.getOrderId().toString();
        this.customerName = paymentRequest.getUser().getName();
        this.customerEmail = paymentRequest.getUser().getLogin();
        this.paymentType = paymentRequest.getPaymentMethod().getPaymentMethodTypeName();
        this.amount = paymentRequest.getAmount();
        this.installments = 0;
        this.creditCard = new SubAcquirerCreditCard(paymentRequest.getCreditCardInformation());
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
