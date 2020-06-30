package br.com.challenge.ifoodpaymentmethods.paymentmethods.process.online;

import br.com.challenge.ifoodpaymentmethods.paymentmethods.process.Payment;
import br.com.challenge.ifoodpaymentmethods.paymentmethods.process.PaymentRequest;
import br.com.challenge.ifoodpaymentmethods.paymentmethods.process.PaymentStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

public class SubAcquirerAProcessPayment implements ProcessPayment {

    private RestTemplate client;

    public SubAcquirerAProcessPayment(RestTemplate client) {
        Assert.notNull(client, "client is required");
        this.client = client;
    }

    @Override
    public Payment process(PaymentRequest paymentRequest) {

        Assert.notNull(paymentRequest, "paymentRequest is required");

        SubAcquirerRequest subAcquirerRequest = new SubAcquirerRequest(paymentRequest);

        try {

            String comunicationUrl = "http://localhost:8081/api/fakeproviders/subacquirera";
            URI uri = new URI(comunicationUrl);
            ResponseEntity<SubAcquirerResponse> response = client.postForEntity(uri, subAcquirerRequest, SubAcquirerResponse.class);
            SubAcquirerResponse subAcquirerResponse = response.getBody();
            ProcessPaymentResponse processPaymentResponse = new ProcessPaymentResponse(subAcquirerResponse.isAccepted("APPROVED"));

            PaymentStatus paymentStatus = (processPaymentResponse.isSuccessed() ? PaymentStatus.FINISHED : PaymentStatus.FAILED);

            return new Payment(paymentRequest.getOrderId(), paymentRequest.getRestaurant(), paymentRequest.getUser(), paymentRequest.getPaymentMethod(), paymentStatus, paymentRequest.getAmount());

        } catch (Exception ex) {
            throw new RuntimeException("Payment process for orderId: " + paymentRequest.getOrderId() + " has failed", ex);
        }
    }
}
