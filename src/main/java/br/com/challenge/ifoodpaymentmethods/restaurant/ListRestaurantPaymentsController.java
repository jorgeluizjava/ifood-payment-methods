package br.com.challenge.ifoodpaymentmethods.restaurant;

import br.com.challenge.ifoodpaymentmethods.paymentmethods.PaymentMethodDTO;
import br.com.challenge.ifoodpaymentmethods.shared.FindById;
import br.com.challenge.ifoodpaymentmethods.shared.fraudster.FraudsterCheck;
import br.com.challenge.ifoodpaymentmethods.user.User;
import br.com.challenge.ifoodpaymentmethods.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
public class ListRestaurantPaymentsController {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private List<FraudsterCheck> fraudsterChecks;

    @GetMapping("/api/restaurants/{id}/payment-methods")
    public List<PaymentMethodDTO> listPaymentsBy(@PathVariable("id") Long id) {

        Restaurant restaurant = FindById.executa(id, restaurantRepository);

        User user = getUser2();

        return restaurant.filterDesiredPaymentMethods(user, fraudsterChecks)
                                .stream()
                                .map(PaymentMethodDTO::new)
                                .collect(toList());

    }

    private User getUser2() {
        return userRepository.findByLogin("usuario3@email.com.br").get();
    }
}
