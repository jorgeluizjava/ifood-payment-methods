package br.com.challenge.ifoodpaymentmethods.paymentmethods;

import org.springframework.data.repository.Repository;

public interface PaymentMethodRepository extends Repository<PaymentMethod, Long> {

    Iterable<PaymentMethod> saveAll(Iterable<PaymentMethod> var1);
}
