package br.com.challenge.ifoodpaymentmethods.paymentmethods.validators;

import br.com.challenge.ifoodpaymentmethods.paymentmethods.PaymentMethod;
import br.com.challenge.ifoodpaymentmethods.paymentmethods.process.PaymentRequestForm;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;

public class CheckIfPaymentMethodExistsValidator implements Validator {

    private EntityManager manager;

    public CheckIfPaymentMethodExistsValidator(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return PaymentRequestForm.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PaymentRequestForm paymentRequestForm = (PaymentRequestForm) target;
        PaymentMethod paymentMethod = manager.find(PaymentMethod.class, paymentRequestForm.getPaymentMethodId());
        if (paymentMethod == null) {
            errors.rejectValue("paymentMethodId", null, "paymentMethodId does not exists!");
        }
    }
}
