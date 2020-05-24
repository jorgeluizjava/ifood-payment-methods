package br.com.challenge.ifoodpaymentmethods.paymentmethods.validators;

import br.com.challenge.ifoodpaymentmethods.paymentmethods.process.PaymentRequest;
import br.com.challenge.ifoodpaymentmethods.restaurant.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;

public class CheckIfRestaurantExistsValidator implements Validator {

    private EntityManager manager;

    public CheckIfRestaurantExistsValidator(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return PaymentRequest.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PaymentRequest paymentRequest = (PaymentRequest) target;
        Restaurant restaurant = manager.find(Restaurant.class, paymentRequest.getRestaurantId());
        if (restaurant == null) {
            errors.rejectValue("restaurantId", null, "restaurantId does not exists!");
        }
    }
}
