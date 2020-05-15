package br.com.challenge.ifoodpaymentmethods.paymentmethods.process.creditcard;

import br.com.challenge.ifoodpaymentmethods.paymentmethods.providers.PaymentMethodProvider;
import br.com.challenge.ifoodpaymentmethods.paymentmethods.providers.PaymentMethodProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class ProcessPayment {

    @Autowired
    private PaymentMethodProviderRepository paymentMethodProviderRepository;

    public void process(PaymentRequest paymentRequest) {

        Assert.notNull(paymentRequest, "paymentRequest is required");

        List<PaymentMethodProvider> paymentMethodProviders = paymentMethodProviderRepository.findAll();
        PaymentMethodProvider paymentMethodProvider = paymentRequest.getPaymentMethodProvider(paymentMethodProviders);

        String comunicationUrl = paymentMethodProvider.getComunicationUrl();

        System.out.println("Executing request to providerName: " + paymentMethodProvider.getDescription() + ", comunicationUrl: " + comunicationUrl
                + " userName: " + paymentRequest.getUser().getName()
                + ", countryCode: " + paymentRequest.getCountryCode().name()
                + ", brand: " + paymentRequest.getBrand().name()
                + ", creditCardInformation: " + paymentRequest.getCreditCardInformation());
    }

}
