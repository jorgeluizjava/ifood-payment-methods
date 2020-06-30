package br.com.challenge.ifoodpaymentmethods.fakeproviders.gateway;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class FakeGatewayProcessPaymentRequest {

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
    @NotBlank
    private String cardNumber;
    @NotBlank
    private String holder;
    @NotBlank
    private String month;
    @NotBlank
    private String year;
    @NotBlank
    @Size(min = 3, max = 3, message = "securityCode must have 3 characters")
    private String securityCode;
    @NotBlank
    private String brand;

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

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
