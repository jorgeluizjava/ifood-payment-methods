package br.com.challenge.ifoodpaymentmethods.fakeproviders.subacquirer;

import br.com.challenge.ifoodpaymentmethods.fakeproviders.FakeProcessPaymentResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
public class FakeProcessPaymentSubAcquirerAController {

    @PostMapping(value = "/api/fakeproviders/subacquirera")
    public FakeProcessPaymentResponse process(@RequestBody @Valid FakeSubAcquirerProcessPaymentRequest fakeSubAcquirerProcessPaymentRequest) {

        if (fakeSubAcquirerProcessPaymentRequest.getAmount().compareTo(new BigDecimal("1000")) <= 0) {
            return new FakeProcessPaymentResponse(fakeSubAcquirerProcessPaymentRequest.getOrderId(), "APPROVED");
        } else {
            return new FakeProcessPaymentResponse(fakeSubAcquirerProcessPaymentRequest.getOrderId(), "REJECTED");
        }
    }
}
