package br.com.challenge.ifoodpaymentmethods.paymentmethods.process.online;

import br.com.challenge.ifoodpaymentmethods.paymentmethods.process.Payment;
import br.com.challenge.ifoodpaymentmethods.paymentmethods.process.PaymentRequest;
import br.com.challenge.ifoodpaymentmethods.paymentmethods.process.PaymentStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotNull;
import java.net.URI;

public class GatewayDProcessPayment implements ProcessPayment {

    @NotNull
    private RestTemplate client;

    public GatewayDProcessPayment(@NotNull RestTemplate client) {
        Assert.notNull(client, "client is required");
        this.client = client;
    }

    @Override
    public Payment process(@NotNull PaymentRequest paymentRequest) {

        Assert.notNull(paymentRequest, "paymentRequest is required");

        GatewayRequest gatewayRequest = new GatewayRequest(paymentRequest);

        try {

            String comunicationUrl = "http://localhost:8081/api/fakeproviders/gatewayd";
            URI uri = new URI(comunicationUrl);
            ResponseEntity<GatewayResponse> response = client.postForEntity(uri, gatewayRequest, GatewayResponse.class);
            GatewayResponse gatewayResponse = response.getBody();
            ProcessPaymentResponse processPaymentResponse = new ProcessPaymentResponse(gatewayResponse.isAccepted("1"));

            PaymentStatus paymentStatus = (processPaymentResponse.isSuccessed() ? PaymentStatus.FINISHED : PaymentStatus.FAILED);

            return new Payment(paymentRequest.getOrderId(), paymentRequest.getRestaurant(), paymentRequest.getUser(), paymentRequest.getPaymentMethod(), paymentStatus, paymentRequest.getAmount());

        } catch (Exception ex) {
            throw new RuntimeException("Payment process for orderId: " + paymentRequest.getOrderId() + " has failed", ex);
        }
    }
}
