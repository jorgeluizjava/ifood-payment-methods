package br.com.challenge.ifoodpaymentmethods.paymentmethods.process.online;

import br.com.challenge.ifoodpaymentmethods.paymentmethods.process.Payment;
import br.com.challenge.ifoodpaymentmethods.paymentmethods.process.PaymentRequest;

public interface ProcessPayment {

    Payment process(PaymentRequest paymentRequest);
}
