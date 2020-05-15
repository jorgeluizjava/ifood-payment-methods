package br.com.challenge.ifoodpaymentmethods.paymentmethods.process.creditcard;

import br.com.challenge.ifoodpaymentmethods.user.User;
import br.com.challenge.ifoodpaymentmethods.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class CreditCardPaymentRequestController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProcessPayment processPayment;

    @PostMapping(value = "/api/payments")
    public void process(@RequestBody @Valid CreditCardPaymentRequest creditCardPaymentRequest) {

        User user = getUser();

        PaymentRequest paymentRequest = creditCardPaymentRequest.toModel(user);
        processPayment.process(paymentRequest);
    }

    private User getUser() {
        return userRepository.findByLogin("usuario1@email.com.br").get();
    }
}
