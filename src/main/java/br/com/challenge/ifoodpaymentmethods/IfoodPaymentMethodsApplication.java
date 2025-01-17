package br.com.challenge.ifoodpaymentmethods;

import br.com.challenge.ifoodpaymentmethods.paymentmethods.PaymentMethod;
import br.com.challenge.ifoodpaymentmethods.paymentmethods.PaymentMethodRepository;
import br.com.challenge.ifoodpaymentmethods.paymentmethods.PaymentMethodType;
import br.com.challenge.ifoodpaymentmethods.paymentmethods.process.online.creditcard.Brand;
import br.com.challenge.ifoodpaymentmethods.paymentmethods.providers.ProviderConfiguration;
import br.com.challenge.ifoodpaymentmethods.restaurant.Restaurant;
import br.com.challenge.ifoodpaymentmethods.restaurant.RestaurantRepository;
import br.com.challenge.ifoodpaymentmethods.shared.CountryCode;
import br.com.challenge.ifoodpaymentmethods.user.User;
import br.com.challenge.ifoodpaymentmethods.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@SpringBootApplication
@EnableJpaRepositories(enableDefaultTransactions = false)
public class IfoodPaymentMethodsApplication implements CommandLineRunner {

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private PaymentMethodRepository paymentMethodRepository;

	@Autowired
	private UserRepository userRepository;

	@PersistenceContext
	private EntityManager manager;

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
		criaUsuario3(allPaymentMethods);
	}

	private void criaUsuario1(List<PaymentMethod> allPaymentMethods) {

		Set<PaymentMethod> desiredPaymentMethods = new HashSet<>(allPaymentMethods);

		User user = new User("usuario1@email.com.br", "Joao", desiredPaymentMethods);
		userRepository.save(user);
	}

	private void criaUsuario2(List<PaymentMethod> allPaymentMethods) {

		Set<PaymentMethod> desiredPaymentMethods = new HashSet<>();
		desiredPaymentMethods.add(allPaymentMethods.get(0));
		desiredPaymentMethods.add(allPaymentMethods.get(2));
		desiredPaymentMethods.add(allPaymentMethods.get(3));

		User user = new User("usuario2@email.com.br", "Caio", desiredPaymentMethods);
		userRepository.save(user);
	}

	private void criaUsuario3(List<PaymentMethod> allPaymentMethods) {

		Set<PaymentMethod> desiredPaymentMethods = new HashSet<>(allPaymentMethods);

		User user = new User("usuario3@email.com.br", "Adr", desiredPaymentMethods);
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
				new PaymentMethod("Apple Pay", PaymentMethodType.DIGITAL_WALLET),
				new PaymentMethod( "Dinheiro", PaymentMethodType.CASH),
				new PaymentMethod( "Crédito - Diners (Maquininha)", PaymentMethodType.POS_MACHINE),
				new PaymentMethod( "Crédito - Elo (Maquininha)", PaymentMethodType.POS_MACHINE),
				new PaymentMethod( "Débito - Diners (Maquininha)", PaymentMethodType.POS_MACHINE),
				new PaymentMethod( "Débito - Elo (Maquininha)", PaymentMethodType.POS_MACHINE)
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

	private List<Brand> allBrands() {
		List<Brand> allBrands = new ArrayList<>();
		allBrands.add(Brand.MASTERCARD);
		allBrands.add(Brand.VISA);
		allBrands.add(Brand.AMEX);
		allBrands.add(Brand.DINERS);
		allBrands.add(Brand.ELO);
		allBrands.add(Brand.HIPERCARD);
		return allBrands;
	}

	private void criaPaymentProviderSubAcquierA(List<Brand> allBrands) {
		Set<Brand> acceptedBrands = new HashSet<>();
		acceptedBrands.add(allBrands.get(3)); // Diners

		Set<CountryCode> acceptedCountries = new HashSet<>();
		acceptedCountries.add(CountryCode.MX);

		ProviderConfiguration providerConfiguration = new ProviderConfiguration("Configuration for subacquire a", acceptedBrands, acceptedCountries);
		manager.persist(providerConfiguration);
	}

	private void criaPaymentProviderSubAcquierB(List<Brand> allBrands) {
		Set<Brand> acceptedBrands = new HashSet<>();
		acceptedBrands.add(allBrands.get(3)); // Diners

		Set<CountryCode> acceptedCountries = new HashSet<>();
		acceptedCountries.add(CountryCode.MX);

		ProviderConfiguration providerConfiguration = new ProviderConfiguration("Configuration for subacquire b", acceptedBrands, acceptedCountries);
		manager.persist(providerConfiguration);
	}

	private void criaPaymentProviderSubAcquierC(List<Brand> allBrands) {
		Set<Brand> acceptedBrands = new HashSet<>();
		acceptedBrands.addAll(allBrands);

		Set<CountryCode> acceptedCountries = new HashSet<>();
		acceptedCountries.add(CountryCode.BR);

		ProviderConfiguration providerConfiguration = new ProviderConfiguration("Configuration for subacquire c", acceptedBrands, acceptedCountries);
		manager.persist(providerConfiguration);
	}

	private void criaPaymentProviderGatewayD(List<Brand> allBrands) {
		Set<Brand> acceptedBrands = new HashSet<>();
		acceptedBrands.add(allBrands.get(0)); // VISA
		acceptedBrands.add(allBrands.get(1)); // MASTER_CARD

		Set<CountryCode> acceptedCountries = new HashSet<>();
		acceptedCountries.add(CountryCode.BR);

		ProviderConfiguration providerConfiguration = new ProviderConfiguration("Configuration for gateway d", acceptedBrands, acceptedCountries);
		manager.persist(providerConfiguration);

	}

	private void criaPaymentProviderGatewayE(List<Brand> allBrands) {
		Set<Brand> acceptedBrands = new HashSet<>();
		acceptedBrands.addAll(allBrands);

		Set<CountryCode> acceptedCountries = new HashSet<>();
		acceptedCountries.add(CountryCode.BR);

		ProviderConfiguration providerConfiguration = new ProviderConfiguration("Configuration for gateway e", acceptedBrands, acceptedCountries);
		manager.persist(providerConfiguration);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean(value = "persistProviderConfiguration")
	@Transactional
	public void persistProviderConfiguration() {
		List<Brand> allBrands = allBrands();
		criaPaymentProviderSubAcquierA(allBrands);
		criaPaymentProviderSubAcquierB(allBrands);
		criaPaymentProviderSubAcquierC(allBrands);
		criaPaymentProviderGatewayD(allBrands);
		criaPaymentProviderGatewayE(allBrands);
	}

}
