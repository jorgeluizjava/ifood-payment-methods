package br.com.challenge.ifoodpaymentmethods.paymentmethods.providers;

import br.com.challenge.ifoodpaymentmethods.paymentmethods.process.PaymentRequest;
import br.com.challenge.ifoodpaymentmethods.paymentmethods.process.online.ProcessPayment;

import java.math.BigDecimal;
import java.util.Optional;

public interface Provider extends Comparable<Provider> {

    BigDecimal cost();

    Optional<ProcessPayment> accept(PaymentRequest paymentRequest);

    @Override
    default int compareTo(Provider other) {
        return this.cost().compareTo(other.cost());
    }
}
