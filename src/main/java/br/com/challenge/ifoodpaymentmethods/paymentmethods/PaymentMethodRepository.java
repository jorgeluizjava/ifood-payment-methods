package br.com.challenge.ifoodpaymentmethods.paymentmethods;

import org.springframework.data.repository.CrudRepository;

public interface PaymentMethodRepository extends CrudRepository<PaymentMethod, Long> {
}
