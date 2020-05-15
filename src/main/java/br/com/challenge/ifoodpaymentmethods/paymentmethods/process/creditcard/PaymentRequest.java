package br.com.challenge.ifoodpaymentmethods.paymentmethods.process.creditcard;

import br.com.challenge.ifoodpaymentmethods.paymentmethods.Brand;
import br.com.challenge.ifoodpaymentmethods.paymentmethods.providers.PaymentMethodProvider;
import br.com.challenge.ifoodpaymentmethods.shared.CountryCode;
import br.com.challenge.ifoodpaymentmethods.user.User;
import org.springframework.util.Assert;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

public class PaymentRequest {

    @NotNull
    private User user;
    @NotNull
    private CountryCode countryCode;
    @NotNull
    private Brand brand;
    @NotNull
    private CreditCardInformation creditCardInformation;
    @NotNull
    @Min(value = 1)
    private BigDecimal amount;

    public PaymentRequest(
                    @NotNull User user,
                    @NotNull CountryCode countryCode,
                    @NotNull Brand brand,
                    @NotNull CreditCardInformation creditCardInformation,
                    @NotNull @Min(value = 1) BigDecimal amount) {

        Assert.notNull(user, "user is required");
        Assert.notNull(countryCode, "countryCode is required");
        Assert.notNull(brand, "brand is required");
        Assert.notNull(creditCardInformation, "creditCardInformation is required");

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("taxAmount is required and must be grather than 0");
        }

        this.user = user;
        this.countryCode = countryCode;
        this.brand = brand;
        this.creditCardInformation = creditCardInformation;
        this.amount = amount;
    }

    public User getUser() {
        return user;
    }

    public CountryCode getCountryCode() {
        return countryCode;
    }

    public Brand getBrand() {
        return brand;
    }

    public CreditCardInformation getCreditCardInformation() {
        return creditCardInformation;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public PaymentMethodProvider getPaymentMethodProvider(List<PaymentMethodProvider> paymentMethodProviders) {
        return paymentMethodProviders
                .stream()
                .filter(currentPaymentMethodProvider -> currentPaymentMethodProvider.accept(countryCode))
                .filter(currentPaymentMethodProvider -> currentPaymentMethodProvider.accept(brand))
                .sorted(Comparator.comparing(currentPaymentMethodProvider -> currentPaymentMethodProvider.getTaxAmount()))
                .findFirst()
                .get();
    }
}
