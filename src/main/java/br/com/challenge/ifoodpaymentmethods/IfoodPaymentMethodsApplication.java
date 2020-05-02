package br.com.challenge.ifoodpaymentmethods;

import br.com.challenge.ifoodpaymentmethods.paymentmethods.*;
import br.com.challenge.ifoodpaymentmethods.restaurant.Restaurant;
import br.com.challenge.ifoodpaymentmethods.restaurant.RestaurantRepository;
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
	private BrandRepository brandRepository;

	public static void main(String[] args) {
		SpringApplication.run(IfoodPaymentMethodsApplication.class, args);
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {

		List<Brand> brands = createBrands();

		List<PaymentMethod> allPaymentMethods = createPaymentMethods(brands);
		createHabibs(allPaymentMethods);
		createLuarVille(allPaymentMethods);
		createParis6(allPaymentMethods);
	}

	private List<Brand> createBrands() {
		List<Brand> brands = Arrays.asList(
				new Brand("VISA"),
				new Brand("MASTER CARD"),
				new Brand("DINERS"),
				new Brand("ELO"),
				new Brand("AMEX"),
				new Brand("CASH"),
				new Brand("CHECK"),
				new Brand("WALLET")
		);
		brandRepository.saveAll(brands);
		return brands;
	}

	private List<PaymentMethod> createPaymentMethods(List<Brand> brands) {

		Brand visa = brands.get(0);
		Brand masterCard = brands.get(1);
		Brand diners = brands.get(2);
		Brand elo = brands.get(3);
		Brand amex = brands.get(4);
		Brand cash = brands.get(5);
		Brand check = brands.get(6);
		Brand wallet = brands.get(7);

		List<PaymentMethod> paymentMethods = Arrays.asList(
				new PaymentMethod("Visa", PaymentMethodType.CREDIT_CARD, visa),
				new PaymentMethod("Diners", PaymentMethodType.CREDIT_CARD, diners),
				new PaymentMethod("Master Card", PaymentMethodType.CREDIT_CARD, masterCard),
				new PaymentMethod("Elo", PaymentMethodType.CREDIT_CARD, elo),
				new PaymentMethod("Amex", PaymentMethodType.CREDIT_CARD, amex),
				new PaymentMethod( "Dinheiro", PaymentMethodType.CASH, cash),
				new PaymentMethod( "Crédito - Diners", PaymentMethodType.POS_MACHINE, diners),
				new PaymentMethod( "Crédito - Elo", PaymentMethodType.POS_MACHINE, elo),
				new PaymentMethod( "Débito - Diners", PaymentMethodType.POS_MACHINE, diners),
				new PaymentMethod( "Débito - Elo", PaymentMethodType.POS_MACHINE, elo)
		);
		paymentMethodRepository.saveAll(paymentMethods);

		return paymentMethods;
	}

	private void createHabibs(List<PaymentMethod> allPaymentMethods) {

		Set<PaymentMethod> paymentMethods = new HashSet<>(allPaymentMethods);
		Restaurant restaurant = new Restaurant("HABIBS", paymentMethods);
		restaurantRepository.save(restaurant);
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
