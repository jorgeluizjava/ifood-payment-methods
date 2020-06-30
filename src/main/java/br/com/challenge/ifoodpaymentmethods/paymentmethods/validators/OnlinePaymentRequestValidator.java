package br.com.challenge.ifoodpaymentmethods.paymentmethods.validators;

import br.com.challenge.ifoodpaymentmethods.paymentmethods.PaymentMethod;
import br.com.challenge.ifoodpaymentmethods.paymentmethods.process.PaymentRequestForm;
import br.com.challenge.ifoodpaymentmethods.paymentmethods.process.online.creditcard.CreditCardInformationDTO;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.Set;


public class OnlinePaymentRequestValidator implements Validator {

    private EntityManager manager;

    public OnlinePaymentRequestValidator(EntityManager manager) {
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

        if (paymentMethod!= null && paymentMethod.isOnline()) {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            javax.validation.Validator validator = factory.getValidator();
            Set<ConstraintViolation<CreditCardInformationDTO>> constraintViolations = validator.validate(paymentRequestForm.getCreditCardInformation());

            constraintViolations.forEach(constraintViolation -> {
                errors.rejectValue("creditCardInformation." + constraintViolation.getPropertyPath().toString(), null, constraintViolation.getMessage());
            });
        }
    }
}
