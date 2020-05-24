package br.com.challenge.ifoodpaymentmethods.paymentmethods.process.online;

import br.com.challenge.ifoodpaymentmethods.paymentmethods.process.Payment;
import br.com.challenge.ifoodpaymentmethods.paymentmethods.providers.PaymentMethodProvider;

public interface ProcessPayment extends ProcessPaymentIdentification {

    ProcessPaymentResponse proccess(Payment payment, PaymentMethodProvider paymentMethodProvider);
}
