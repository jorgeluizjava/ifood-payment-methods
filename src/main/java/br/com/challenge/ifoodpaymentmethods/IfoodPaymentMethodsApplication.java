package br.com.challenge.ifoodpaymentmethods;

import br.com.challenge.ifoodpaymentmethods.paymentmethods.PaymentMethod;
import br.com.challenge.ifoodpaymentmethods.paymentmethods.PaymentMethodRepository;
import br.com.challenge.ifoodpaymentmethods.paymentmethods.PaymentMethodType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableJpaRepositories(enableDefaultTransactions = false)
public class IfoodPaymentMethodsApplication implements CommandLineRunner {

	@Autowired
	private PaymentMethodRepository paymentMethodRepository;

	public static void main(String[] args) {
		SpringApplication.run(IfoodPaymentMethodsApplication.class, args);
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		List<PaymentMethod> paymentMethods = Arrays.asList(
				new PaymentMethod(true, "Visa", PaymentMethodType.CREDIT_CARD),
				new PaymentMethod(true, "Diners", PaymentMethodType.CREDIT_CARD),
				new PaymentMethod(true, "Master Card", PaymentMethodType.CREDIT_CARD),
				new PaymentMethod(true, "Elo", PaymentMethodType.CREDIT_CARD),
				new PaymentMethod(true, "Amex", PaymentMethodType.CREDIT_CARD),
				new PaymentMethod(false, "Dinheiro", PaymentMethodType.CASH),
				new PaymentMethod(false, "Crédito - Dinners", PaymentMethodType.POS_MACHINE),
				new PaymentMethod(false, "Crédito - Elo", PaymentMethodType.POS_MACHINE),
				new PaymentMethod(false, "Débito - Dinners", PaymentMethodType.POS_MACHINE),
				new PaymentMethod(false, "Débito - Elo", PaymentMethodType.POS_MACHINE)
		);

		paymentMethodRepository.saveAll(paymentMethods);
	}
}
