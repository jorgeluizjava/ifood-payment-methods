package br.com.challenge.ifoodpaymentmethods;

import br.com.challenge.ifoodpaymentmethods.paymentmethods.PaymentMethod;
import br.com.challenge.ifoodpaymentmethods.paymentmethods.PaymentMethodRepository;
import br.com.challenge.ifoodpaymentmethods.paymentmethods.PaymentMethodType;
import br.com.challenge.ifoodpaymentmethods.restaurant.Restaurant;
import br.com.challenge.ifoodpaymentmethods.restaurant.RestaurantRepository;
import br.com.challenge.ifoodpaymentmethods.user.User;
import br.com.challenge.ifoodpaymentmethods.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
@EnableJpaRepositories(enableDefaultTransactions = false)
public class IfoodPaymentMethodsApplication implements CommandLineRunner {

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private PaymentMethodRepository paymentMethodRepository;

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(IfoodPaymentMethodsApplication.class, args);
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {

		List<PaymentMethod> allPaymentMethods = createPaymentMethods();
		createHabibs(allPaymentMethods);
		createLuarVille(allPaymentMethods);
		createParis6(allPaymentMethods);

		criaUsuario1(allPaymentMethods);
		criaUsuario2(allPaymentMethods);
	}

	private void criaUsuario1(List<PaymentMethod> allPaymentMethods) {

		Set<PaymentMethod> desiredPaymentMethods = new HashSet<>(allPaymentMethods);

		User user = new User("usuario1@email.com.br", desiredPaymentMethods);
		userRepository.save(user);
	}

	private void criaUsuario2(List<PaymentMethod> allPaymentMethods) {

		Set<PaymentMethod> desiredPaymentMethods = new HashSet<>();
		desiredPaymentMethods.add(allPaymentMethods.get(0));
		desiredPaymentMethods.add(allPaymentMethods.get(2));
		desiredPaymentMethods.add(allPaymentMethods.get(3));

		User user = new User("usuario2@email.com.br", desiredPaymentMethods);
		userRepository.save(user);
	}

	private void createHabibs(List<PaymentMethod> allPaymentMethods) {

		Set<PaymentMethod> paymentMethods = new HashSet<>(allPaymentMethods);
		Restaurant restaurant = new Restaurant("HABIBS", paymentMethods);
		restaurantRepository.save(restaurant);
	}

	private List<PaymentMethod> createPaymentMethods() {

		List<PaymentMethod> paymentMethods = Arrays.asList(
				new PaymentMethod("Visa", PaymentMethodType.CREDIT_CARD),
				new PaymentMethod("Diners", PaymentMethodType.CREDIT_CARD),
				new PaymentMethod("Master Card", PaymentMethodType.CREDIT_CARD),
				new PaymentMethod("Elo", PaymentMethodType.CREDIT_CARD),
				new PaymentMethod("Amex", PaymentMethodType.CREDIT_CARD),
				new PaymentMethod( "Dinheiro", PaymentMethodType.CASH),
				new PaymentMethod( "Crédito - Diners", PaymentMethodType.POS_MACHINE),
				new PaymentMethod( "Crédito - Elo", PaymentMethodType.POS_MACHINE),
				new PaymentMethod( "Débito - Diners", PaymentMethodType.POS_MACHINE),
				new PaymentMethod( "Débito - Elo", PaymentMethodType.POS_MACHINE)
		);
		paymentMethodRepository.saveAll(paymentMethods);

		return paymentMethods;
	}

	private void createLuarVille(List<PaymentMethod> allPaymentMethods) {

		Set<PaymentMethod> paymentMethods = new HashSet<>();
		paymentMethods.add(allPaymentMethods.get(0));
		paymentMethods.add(allPaymentMethods.get(1));
		paymentMethods.add(allPaymentMethods.get(2));
		paymentMethods.add(allPaymentMethods.get(3));
		paymentMethods.add(allPaymentMethods.get(4));

		Restaurant restaurant = new Restaurant("LUAR VILLE", paymentMethods);
		restaurantRepository.save(restaurant);

	}

	private void createParis6(List<PaymentMethod> allPaymentMethods) {

		Set<PaymentMethod> paymentMethods = new HashSet<>();
		paymentMethods.add(allPaymentMethods.get(9));
		paymentMethods.add(allPaymentMethods.get(8));
		paymentMethods.add(allPaymentMethods.get(7));
		paymentMethods.add(allPaymentMethods.get(6));
		paymentMethods.add(allPaymentMethods.get(5));

		Restaurant restaurant = new Restaurant("PARIS 6", paymentMethods);
		restaurantRepository.save(restaurant);
	}

}
