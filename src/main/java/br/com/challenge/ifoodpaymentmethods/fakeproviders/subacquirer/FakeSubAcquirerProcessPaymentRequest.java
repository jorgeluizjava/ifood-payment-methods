package br.com.challenge.ifoodpaymentmethods.fakeproviders.subacquirer;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class FakeSubAcquirerProcessPaymentRequest {

    @NotBlank
    private String orderId;
    @NotBlank
    private String customerName;
    @NotBlank
    @Email
    private String customerEmail;
    @NotBlank
    private String paymentType;
    @NotNull
    @Min(value = 1)
    private BigDecimal amount;
    @NotNull
    private Integer installments;
    @Valid
    private FakeCreditCard creditCard;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getInstallments() {
        return installments;
    }

    public void setInstallments(Integer installments) {
        this.installments = installments;
    }

    public FakeCreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(FakeCreditCard creditCard) {
        this.creditCard = creditCard;
    }
}


