package br.com.challenge.ifoodpaymentmethods.paymentmethods.process.online;

import br.com.challenge.ifoodpaymentmethods.paymentmethods.PaymentMethod;
import br.com.challenge.ifoodpaymentmethods.paymentmethods.process.Payment;
import br.com.challenge.ifoodpaymentmethods.paymentmethods.process.online.creditcard.CreditCardInformation;
import br.com.challenge.ifoodpaymentmethods.user.User;
import org.springframework.util.Assert;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class GatewayRequest {

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

    @Deprecated
    public GatewayRequest() {}

    public GatewayRequest(Payment payment) {

        Assert.notNull(payment, "payment is required");

        User user = payment.getUser();
        PaymentMethod paymentMethod = payment.getPaymentMethod();
        CreditCardInformation creditCardInformation = payment.getCreditCardInformation();

        this.orderId = payment.getOrderId().toString();
        this.customerName = user.getName();
        this.customerEmail = user.getLogin();
        this.paymentType = paymentMethod.getPaymentMethodType().name();
        this.amount = payment.getAmount();
        this.cardNumber = creditCardInformation.getCardNumber();
        this.holder = creditCardInformation.getNameOnCard();
        this.month = creditCardInformation.getExpirationDate().getMonth().toString();
        this.year = String.valueOf(creditCardInformation.getExpirationDate().getYear());
        this.securityCode = creditCardInformation.getSecurityCode();
        this.brand = creditCardInformation.getBrand().name();
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

    public String getCardNumber() {
        return cardNumber;
    }

    public String getHolder() {
        return holder;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public String getBrand() {
        return brand;
    }
}
