package br.com.challenge.ifoodpaymentmethods.paymentmethods.providers;

import br.com.challenge.ifoodpaymentmethods.paymentmethods.process.PaymentRequest;
import br.com.challenge.ifoodpaymentmethods.paymentmethods.process.online.ProcessPayment;
import br.com.challenge.ifoodpaymentmethods.paymentmethods.process.online.SubAcquirerCProcessPayment;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Optional;

public class SubAcquireC implements Provider {

    @NotNull
    private RestTemplate client;

    @NotNull
    private ProviderConfiguration providerConfiguration;

    private ProviderType providerType = ProviderType.SUB_ACQUIRER;

    public SubAcquireC(@NotNull ProviderConfiguration providerConfiguration, RestTemplate client) {
        Assert.notNull(client, "client is required");
        Assert.notNull(providerConfiguration, "providerConfiguration is required");
        this.providerConfiguration = providerConfiguration;
        this.client = client;
    }

    @Override
    public BigDecimal cost() {
        return new BigDecimal("5");
    }

    @Override
    public Optional<ProcessPayment> accept(PaymentRequest paymentRequest) {

        Assert.notNull(paymentRequest, "paymentRequest is required");

        if (!providerConfiguration.accept(paymentRequest.getCountryCode())) {
            return Optional.empty();
        }
        if (!providerConfiguration.accept(paymentRequest.getCreditCardInformation().getBrand())) {
            return Optional.empty();
        }
        return Optional.of(new SubAcquirerCProcessPayment(client));
    }
}
