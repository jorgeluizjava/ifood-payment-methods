package br.com.challenge.ifoodpaymentmethods.paymentmethods.process;

import br.com.challenge.ifoodpaymentmethods.paymentmethods.PaymentMethod;
import br.com.challenge.ifoodpaymentmethods.paymentmethods.process.online.creditcard.CreditCardInformation;
import br.com.challenge.ifoodpaymentmethods.restaurant.Restaurant;
import br.com.challenge.ifoodpaymentmethods.shared.CountryCode;
import br.com.challenge.ifoodpaymentmethods.user.User;
import org.springframework.util.Assert;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class PaymentRequest {

    @NotNull(message = "countryCode is required")
    private CountryCode countryCode;

    @NotNull(message = "orderId is required")
    private Long orderId;

    @NotNull(message = "restaurant is required")
    private Restaurant restaurant;

    @NotNull(message = "user is required")
    private User user;

    @NotNull(message = "paymentMethod is required")
    private PaymentMethod paymentMethod;

    @Min(value = 1, message = "amount must be grather than 0")
    private BigDecimal amount;

    private CreditCardInformation creditCardInformation;

    public PaymentRequest(
            @NotNull CountryCode countryCode,
            @NotNull Long orderId,
            @NotNull Restaurant restaurant,
            @NotNull User user,
            @NotNull PaymentMethod paymentMethod,
            @Min(value = 1) BigDecimal amount) {

        Assert.notNull(countryCode, "countryCode is required");
        Assert.notNull(orderId, "orderId is required");
        Assert.notNull(restaurant, "restaurant is required");
        Assert.notNull(user, "user is required");
        Assert.notNull(paymentMethod, "paymentMethod is required");

        Assert.notNull(amount, "amount is required");
        Assert.isTrue(amount.compareTo(BigDecimal.ZERO) > 0, "");

        this.countryCode = countryCode;
        this.orderId = orderId;
        this.restaurant = restaurant;
        this.user = user;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
    }

    public void setCreditCardInformation(CreditCardInformation creditCardInformation) {
        Assert.notNull(creditCardInformation, "creditCardInformation must not be null");
        this.creditCardInformation = creditCardInformation;
    }

    public boolean isOnline() {
        return this.paymentMethod.isOnline();
    }

    public CountryCode getCountryCode() {
        return countryCode;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public User getUser() {
        return user;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public CreditCardInformation getCreditCardInformation() {
        return creditCardInformation;
    }
}
