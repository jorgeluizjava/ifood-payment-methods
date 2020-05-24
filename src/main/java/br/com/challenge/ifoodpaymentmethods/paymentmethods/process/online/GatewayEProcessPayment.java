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
public class GatewayEProcessPayment implements ProcessPayment {

    @Autowired
    private RestTemplate client;

    @Override
    public ProcessPaymentResponse proccess(Payment payment, PaymentMethodProvider paymentMethodProvider) {

        Assert.notNull(payment, "payment is required");
        Assert.notNull(paymentMethodProvider, "paymentMethodProvider is required");

        GatewayRequest gatewayRequest = new GatewayRequest(payment);

        try {

            URI uri = new URI(paymentMethodProvider.getComunicationUrl());
            ResponseEntity<GatewayResponse> response = client.postForEntity(uri, gatewayRequest, GatewayResponse.class);
            GatewayResponse gatewayResponse = response.getBody();

            return new ProcessPaymentResponse(gatewayResponse.getStatus().equals("1"));

        } catch (Exception ex) {
            throw new RuntimeException("Payment process for orderId: " + payment.getOrderId() + " has failed", ex);
        }
    }

    @Override
    public String getType() {
        return "GATEWAYE";
    }
}
