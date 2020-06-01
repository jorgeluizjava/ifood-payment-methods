package br.com.challenge.ifoodpaymentmethods.paymentmethods.providers;

import br.com.challenge.ifoodpaymentmethods.paymentmethods.process.Payment;
import br.com.challenge.ifoodpaymentmethods.paymentmethods.process.PaymentRequest;
import br.com.challenge.ifoodpaymentmethods.paymentmethods.process.online.ProcessPayment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class LowCostProvider {

    @Autowired
    private Set<Provider> providers;

    public Payment pay(PaymentRequest paymentRequest) {

        Optional<ProcessPayment> optionalProcessPayment = providers
                                                                .stream()
                                                                .sorted()
                                                                .map(provider -> provider.accept(paymentRequest))
                                                                .filter(Optional::isPresent)
                                                                .map(Optional::get)
                                                                .findFirst();

        if (!optionalProcessPayment.isPresent()) {
            throw new IllegalStateException("ERROR, ProcessPayment not found.");
        }

        ProcessPayment processPayment = optionalProcessPayment.get();
        return processPayment.process(paymentRequest);
    }
}
