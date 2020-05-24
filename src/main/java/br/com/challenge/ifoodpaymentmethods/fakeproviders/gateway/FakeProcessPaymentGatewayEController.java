package br.com.challenge.ifoodpaymentmethods.fakeproviders.gateway;

import br.com.challenge.ifoodpaymentmethods.fakeproviders.FakeProcessPaymentResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
public class FakeProcessPaymentGatewayEController {

    @PostMapping(value = "/api/fakeproviders/gatewaye")
    public FakeProcessPaymentResponse process(@RequestBody @Valid FakeGatewayProcessPaymentRequest fakeGatewayProcessPaymentRequest) {
        if (fakeGatewayProcessPaymentRequest.getAmount().compareTo(new BigDecimal("5000")) <= 0) {
            return new FakeProcessPaymentResponse(fakeGatewayProcessPaymentRequest.getOrderId(), "1");
        } else {
            return new FakeProcessPaymentResponse(fakeGatewayProcessPaymentRequest.getOrderId(), "0");
        }
    }
}
