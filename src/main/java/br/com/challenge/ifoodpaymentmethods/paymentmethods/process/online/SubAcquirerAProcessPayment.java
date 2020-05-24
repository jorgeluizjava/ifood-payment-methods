package br.com.challenge.ifoodpaymentmethods.paymentmethods.process.online;

import br.com.challenge.ifoodpaymentmethods.paymentmethods.process.Payment;
import br.com.challenge.ifoodpaymentmethods.paymentmethods.providers.PaymentMethodProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
public class SubAcquirerAProcessPayment implements ProcessPayment {

    @Autowired
    private RestTemplate client;

    @Override
    public ProcessPaymentResponse proccess(Payment payment, PaymentMethodProvider paymentMethodProvider) {

        Assert.notNull(payment, "payment is required");
        Assert.notNull(paymentMethodProvider, "paymentMethodProvider is required");

        SubAcquirerRequest subAcquirerRequest = new SubAcquirerRequest(payment);

        try {

            URI uri = new URI(paymentMethodProvider.getComunicationUrl());
            ResponseEntity<SubAcquirerResponse> response = client.postForEntity(uri, subAcquirerRequest, SubAcquirerResponse.class);
            SubAcquirerResponse subAcquirerResponse = response.getBody();

            return new ProcessPaymentResponse(subAcquirerResponse.getStatus().equals("APPROVED"));

        } catch (Exception ex) {
            throw new RuntimeException("Payment process for orderId: " + payment.getOrderId() + " has failed", ex);
        }
    }

    @Override
    public String getType() {
        return "SUBACQUIRERA";
    }
}
