package br.com.challenge.ifoodpaymentmethods.restaurant;

import br.com.challenge.ifoodpaymentmethods.paymentmethods.PaymentMethodDTO;
import br.com.challenge.ifoodpaymentmethods.shared.FindById;
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

    @GetMapping("/api/restaurants/{id}/payment-methods")
    public List<PaymentMethodDTO> listPaymentsBy(@PathVariable("id") Long id) {

        Restaurant restaurant = FindById.executa(id, restaurantRepository);

        return restaurant.getPaymentMethods()
                .stream()
                .map(PaymentMethodDTO::new)
                .collect(toList());

    }
}
