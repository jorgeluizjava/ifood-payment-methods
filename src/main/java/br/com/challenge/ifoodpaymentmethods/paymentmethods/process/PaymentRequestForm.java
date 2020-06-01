package br.com.challenge.ifoodpaymentmethods.paymentmethods.process;

import br.com.challenge.ifoodpaymentmethods.paymentmethods.PaymentMethod;
import br.com.challenge.ifoodpaymentmethods.paymentmethods.process.online.creditcard.CreditCardInformationDTO;
import br.com.challenge.ifoodpaymentmethods.restaurant.Restaurant;
import br.com.challenge.ifoodpaymentmethods.shared.CountryCode;
import br.com.challenge.ifoodpaymentmethods.shared.FindById;
import br.com.challenge.ifoodpaymentmethods.user.User;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class PaymentRequestForm {

    @NotNull(message = "orderId is required")
    private Long orderId;

    @NotNull(message = "restaurantId is required")
    private Long restaurantId;

    @NotNull(message = "userId is required")
    private Long userId;

    @NotNull(message = "paymentMethodId is required")
    private Long paymentMethodId;

    @Min(value = 1, message = "amount must be grather than 0")
    private BigDecimal amount;

    private CountryCode countryCode;

    private CreditCardInformationDTO creditCardInformation;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(Long paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public CountryCode getCountryCode() {
        return countryCode;
    }

    public CreditCardInformationDTO getCreditCardInformation() {
        return creditCardInformation;
    }

    public PaymentRequest toModel(EntityManager manager) {

        Assert.notNull(manager, "manager is required");

        User user = FindById.execute(getUserId(), manager, User.class);
        Restaurant restaurant = FindById.execute(getRestaurantId(), manager, Restaurant.class);
        PaymentMethod paymentMethod = FindById.execute(getPaymentMethodId(), manager, PaymentMethod.class);

        PaymentRequest paymentRequest = new PaymentRequest(countryCode, orderId, restaurant, user, paymentMethod, amount);

        if (paymentMethod.isOnline()) {
            paymentRequest.setCreditCardInformation(creditCardInformation.toModel());
        }

        return paymentRequest;
    }

}
