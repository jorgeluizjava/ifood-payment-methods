package br.com.challenge.ifoodpaymentmethods.paymentmethods;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
public class ListPaymentMethodsController {

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @GetMapping("/api/payment-methods")
    public List<PaymentMethodsDTO> list() {

        return paymentMethodRepository
                    .findAll()
                    .stream()
                    .map(PaymentMethodsDTO::new)
                    .collect(toList());

    }
}
