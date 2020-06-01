package br.com.challenge.ifoodpaymentmethods.paymentmethods.validators;

import br.com.challenge.ifoodpaymentmethods.paymentmethods.process.PaymentRequestForm;
import br.com.challenge.ifoodpaymentmethods.restaurant.Restaurant;
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
        return PaymentRequestForm.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PaymentRequestForm paymentRequestForm = (PaymentRequestForm) target;
        Restaurant restaurant = manager.find(Restaurant.class, paymentRequestForm.getRestaurantId());
        if (restaurant == null) {
            errors.rejectValue("restaurantId", null, "restaurantId does not exists!");
        }
    }
}
