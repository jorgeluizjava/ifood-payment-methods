package br.com.challenge.ifoodpaymentmethods.paymentmethods.process.online;

import br.com.challenge.ifoodpaymentmethods.paymentmethods.process.Payment;
import br.com.challenge.ifoodpaymentmethods.paymentmethods.providers.PaymentMethodProvider;
import br.com.challenge.ifoodpaymentmethods.paymentmethods.providers.PaymentMethodProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.util.List;

@RestController
public class OnlinePaymentRequestController {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private PaymentMethodProviderRepository paymentMethodProviderRepository;

    @Autowired
    private List<ProcessPayment> processPayments;

    @Transactional
    @PostMapping(value = "/api/online-payments")
    public void process(@RequestBody @Valid OnlinePaymentRequest onlinePaymentRequest) {

        Payment payment = onlinePaymentRequest.toModel(manager);
        manager.persist(payment);

        List<PaymentMethodProvider> paymentMethodProviders = paymentMethodProviderRepository.findAll();
        PaymentMethodProvider paymentMethodProvider = onlinePaymentRequest.getPaymentMethodProvider(paymentMethodProviders);

        ProcessPayment processPayment = paymentMethodProvider.getProcessPayment(processPayments);
        ProcessPaymentResponse processPaymentResponse = processPayment.proccess(payment, paymentMethodProvider);

        payment.register(processPaymentResponse);
        manager.merge(payment);
    }

}
