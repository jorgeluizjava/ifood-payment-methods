package br.com.challenge.ifoodpaymentmethods.paymentmethods.process;

import br.com.challenge.ifoodpaymentmethods.paymentmethods.providers.LowCostProvider;
import br.com.challenge.ifoodpaymentmethods.paymentmethods.validators.CheckIfPaymentMethodExistsValidator;
import br.com.challenge.ifoodpaymentmethods.paymentmethods.validators.CheckIfRestaurantExistsValidator;
import br.com.challenge.ifoodpaymentmethods.paymentmethods.validators.OnlinePaymentRequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

@RestController
public class PaymentRequestController {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private LowCostProvider lowCostProvider;

    @InitBinder(value = "paymentRequestForm")
    public void init(WebDataBinder dataBinder) {
        dataBinder.addValidators(new OnlinePaymentRequestValidator(manager));
        dataBinder.addValidators(new CheckIfRestaurantExistsValidator(manager));
        dataBinder.addValidators(new CheckIfPaymentMethodExistsValidator(manager));
    }

    @PostMapping(value = "/api/payments")
    @Transactional
    public void process(@RequestBody @Valid PaymentRequestForm paymentRequestForm) {

        PaymentRequest paymentRequest = paymentRequestForm.toModel(manager);

        if (!paymentRequest.isOnline()) {
            Payment payment = new Payment(paymentRequest.getOrderId(), paymentRequest.getRestaurant(), paymentRequest.getUser(), paymentRequest.getPaymentMethod(), PaymentStatus.STARTED, paymentRequest.getAmount());
            manager.persist(payment);
        } else {

            Payment payment = lowCostProvider.pay(paymentRequest);
            manager.persist(payment);
        }

    }
}
