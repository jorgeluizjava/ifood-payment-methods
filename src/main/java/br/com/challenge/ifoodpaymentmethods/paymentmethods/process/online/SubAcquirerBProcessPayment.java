package br.com.challenge.ifoodpaymentmethods.paymentmethods.process.online;

import br.com.challenge.ifoodpaymentmethods.paymentmethods.process.Payment;
import br.com.challenge.ifoodpaymentmethods.paymentmethods.process.PaymentRequest;
import br.com.challenge.ifoodpaymentmethods.paymentmethods.process.PaymentStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotNull;
import java.net.URI;

public class SubAcquirerBProcessPayment implements ProcessPayment {

    @NotNull
    private RestTemplate client;

    public SubAcquirerBProcessPayment(@NotNull RestTemplate client) {
        Assert.notNull(client, "client is required");
        this.client = client;
    }

    @Override
    public Payment process(@NotNull PaymentRequest paymentRequest) {

        Assert.notNull(paymentRequest, "paymentRequest is required");

        SubAcquirerRequest subAcquirerRequest = new SubAcquirerRequest(paymentRequest);

        try {

            String comunicationUrl = "http://localhost:8081/api/fakeproviders/subacquirerb";
            URI uri = new URI(comunicationUrl);
            ResponseEntity<SubAcquirerResponse> response = client.postForEntity(uri, subAcquirerRequest, SubAcquirerResponse.class);
            SubAcquirerResponse subAcquirerResponse = response.getBody();
            ProcessPaymentResponse processPaymentResponse = new ProcessPaymentResponse(subAcquirerResponse.isAccepted("SUCCESS"));

            PaymentStatus paymentStatus = (processPaymentResponse.isSuccessed() ? PaymentStatus.FINISHED : PaymentStatus.FAILED);

            return new Payment(paymentRequest.getOrderId(), paymentRequest.getRestaurant(), paymentRequest.getUser(), paymentRequest.getPaymentMethod(), paymentStatus, paymentRequest.getAmount());

        } catch (Exception ex) {
            throw new RuntimeException("Payment process for orderId: " + paymentRequest.getOrderId() + " has failed", ex);
        }
    }
}
