package br.com.challenge.ifoodpaymentmethods.paymentmethods.process.offline;

import br.com.challenge.ifoodpaymentmethods.paymentmethods.PaymentMethod;
import br.com.challenge.ifoodpaymentmethods.paymentmethods.process.Payment;
import br.com.challenge.ifoodpaymentmethods.paymentmethods.process.PaymentRequest;
import br.com.challenge.ifoodpaymentmethods.paymentmethods.process.PaymentStatus;
import br.com.challenge.ifoodpaymentmethods.restaurant.Restaurant;
import br.com.challenge.ifoodpaymentmethods.shared.FindById;
import br.com.challenge.ifoodpaymentmethods.user.User;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;

public class OfflinePaymentRequest extends PaymentRequest {

    public Payment toModel(EntityManager manager) {

        Assert.notNull(manager, "manager is required");

        User user = FindById.execute(getUserId(), manager, User.class);
        Restaurant restaurant = FindById.execute(getRestaurantId(), manager, Restaurant.class);
        PaymentMethod paymentMethod = FindById.execute(getPaymentMethodId(), manager, PaymentMethod.class);

        return new Payment(getOrderId(), restaurant, user, paymentMethod, PaymentStatus.STARTED, getAmount());
    }
}
