package br.com.challenge.ifoodpaymentmethods.paymentmethods.providers;

import org.springframework.data.repository.Repository;

import java.util.List;

public interface PaymentMethodProviderRepository extends Repository<PaymentMethodProvider, Long> {

    PaymentMethodProvider save(PaymentMethodProvider paymentMethodProvider);

    List<PaymentMethodProvider> findAll();
}
