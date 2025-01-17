package br.com.challenge.ifoodpaymentmethods.paymentmethods;

import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface PaymentMethodRepository extends Repository<PaymentMethod, Long> {

    Iterable<PaymentMethod> saveAll(Iterable<PaymentMethod> var1);

    Optional<PaymentMethod> findById(Long id);
}
