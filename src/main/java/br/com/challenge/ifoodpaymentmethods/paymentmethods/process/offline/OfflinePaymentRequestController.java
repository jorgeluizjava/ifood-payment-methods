package br.com.challenge.ifoodpaymentmethods.paymentmethods.process.offline;

import br.com.challenge.ifoodpaymentmethods.paymentmethods.process.Payment;
import br.com.challenge.ifoodpaymentmethods.paymentmethods.validators.CheckIfPaymentMethodExistsValidator;
import br.com.challenge.ifoodpaymentmethods.paymentmethods.validators.CheckIfRestaurantExistsValidator;
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
public class OfflinePaymentRequestController {

    @PersistenceContext
    private EntityManager manager;

    @InitBinder(value = {"offlinePaymentRequest"})
    public void dataBinder(WebDataBinder dataBinder) {
        dataBinder.addValidators(new CheckIfRestaurantExistsValidator(manager));
        dataBinder.addValidators(new CheckIfPaymentMethodExistsValidator(manager));
    }

    @Transactional
    @PostMapping(value = "/api/offline-payments")
    public void process(@RequestBody @Valid OfflinePaymentRequest offlinePaymentRequest) {
        Payment payment = offlinePaymentRequest.toModel(manager);
        manager.persist(payment);
    }
}
