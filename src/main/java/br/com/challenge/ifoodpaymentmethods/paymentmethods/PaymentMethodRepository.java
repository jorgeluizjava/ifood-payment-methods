package br.com.challenge.ifoodpaymentmethods.paymentmethods;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
}
