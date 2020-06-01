package br.com.challenge.ifoodpaymentmethods.paymentmethods.providers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
public class ProvidersFactory {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private RestTemplate client;

    @Bean
    @DependsOn(value = "persistProviderConfiguration")
    public Provider subAcquirerA() {
        ProviderConfiguration providerConfiguration = manager.find(ProviderConfiguration.class, 1L);
        return new SubAcquireA(providerConfiguration, client);
    }

    @Bean
    @DependsOn(value = "persistProviderConfiguration")
    public Provider subAcquirerB() {
        ProviderConfiguration providerConfiguration = manager.find(ProviderConfiguration.class, 2L);
        return new SubAcquireB(providerConfiguration, client);
    }

    @Bean
    @DependsOn(value = "persistProviderConfiguration")
    public Provider subAcquirerC() {
        ProviderConfiguration providerConfiguration = manager.find(ProviderConfiguration.class, 3L);
        return new SubAcquireC(providerConfiguration, client);
    }

    @Bean
    @DependsOn(value = "persistProviderConfiguration")
    public Provider gatewayD() {
        ProviderConfiguration providerConfiguration = manager.find(ProviderConfiguration.class, 4L);
        return new GatewayD(providerConfiguration, client);
    }

    @Bean
    @DependsOn(value = "persistProviderConfiguration")
    public Provider gatewayE() {
        ProviderConfiguration providerConfiguration = manager.find(ProviderConfiguration.class, 5L);
        return new GatewayE(providerConfiguration, client);
    }

}
