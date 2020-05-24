package br.com.challenge.ifoodpaymentmethods.paymentmethods.process.online;

import br.com.challenge.ifoodpaymentmethods.paymentmethods.PaymentMethod;
import br.com.challenge.ifoodpaymentmethods.paymentmethods.process.Payment;
import br.com.challenge.ifoodpaymentmethods.paymentmethods.process.PaymentRequest;
import br.com.challenge.ifoodpaymentmethods.paymentmethods.process.PaymentStatus;
import br.com.challenge.ifoodpaymentmethods.paymentmethods.process.online.creditcard.CreditCardInformation;
import br.com.challenge.ifoodpaymentmethods.paymentmethods.process.online.creditcard.CreditCardInformationDTO;
import br.com.challenge.ifoodpaymentmethods.paymentmethods.providers.PaymentMethodProvider;
import br.com.challenge.ifoodpaymentmethods.restaurant.Restaurant;
import br.com.challenge.ifoodpaymentmethods.shared.CountryCode;
import br.com.challenge.ifoodpaymentmethods.shared.FindById;
import br.com.challenge.ifoodpaymentmethods.user.User;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Comparator;
import java.util.List;

public class OnlinePaymentRequest extends PaymentRequest {

    @NotNull(message = "countryCode id required")
    private CountryCode countryCode;

    @Valid
    private CreditCardInformationDTO creditCardInformation;

    public void setCountryCode(CountryCode countryCode) {
        this.countryCode = countryCode;
    }

    public void setCreditCardInformation(CreditCardInformationDTO creditCardInformation) {
        this.creditCardInformation = creditCardInformation;
    }

    public CreditCardInformationDTO getCreditCardInformation() {
        return creditCardInformation;
    }

    public Payment toModel(EntityManager manager) {

        Assert.notNull(manager, "manager is required");

        User user = FindById.execute(getUserId(), manager, User.class);
        Restaurant restaurant = FindById.execute(getRestaurantId(), manager, Restaurant.class);
        PaymentMethod paymentMethod = FindById.execute(getPaymentMethodId(), manager, PaymentMethod.class);
        CreditCardInformation ccInformation = creditCardInformation.toModel();

        return new Payment(getOrderId(), restaurant, user, paymentMethod, PaymentStatus.STARTED, getAmount(), ccInformation);
    }

    public PaymentMethodProvider getPaymentMethodProvider(List<PaymentMethodProvider> paymentMethodProviders) {
        return paymentMethodProviders
                        .stream()
                        .filter(currentPaymentMethodProvider -> currentPaymentMethodProvider.accept(countryCode))
                        .filter(currentPaymentMethodProvider -> currentPaymentMethodProvider.accept(creditCardInformation.getBrand()))
                        .sorted(Comparator.comparing(PaymentMethodProvider::getTaxAmount))
                        .findFirst()
                        .get();

    }

}
