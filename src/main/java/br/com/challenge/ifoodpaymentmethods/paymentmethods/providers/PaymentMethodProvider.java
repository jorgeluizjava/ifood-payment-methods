package br.com.challenge.ifoodpaymentmethods.paymentmethods.providers;

import br.com.challenge.ifoodpaymentmethods.paymentmethods.Brand;
import br.com.challenge.ifoodpaymentmethods.shared.CountryCode;
import org.hibernate.validator.constraints.URL;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "payment_method_provider")
public class PaymentMethodProvider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String description;

    @NotNull
    @Min(value = 1)
    private BigDecimal taxAmount;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PaymentMethodProviderType paymentMethodProviderType;

    @CollectionTable(name = "payment_method_provider_accepted_brands")
    @ElementCollection(targetClass = Brand.class)
    @Enumerated(EnumType.STRING)
    private Set<Brand> acceptedBrands = new HashSet<>();

    @CollectionTable(name = "payment_method_provider_accepted_countries")
    @ElementCollection(targetClass = CountryCode.class)
    @Enumerated(EnumType.STRING)
    private Set<CountryCode> acceptedCountries = new HashSet<>();

    @NotNull
    @URL
    private String comunicationUrl;

    @Deprecated
    PaymentMethodProvider(){}

    public PaymentMethodProvider(
                    @NotBlank String description,
                    @NotNull @Min(value = 1) BigDecimal taxAmount,
                    @NotNull PaymentMethodProviderType paymentMethodProviderType,
                    @NotNull @NotEmpty Set<Brand> acceptedBrands,
                    @NotNull @NotEmpty Set<CountryCode> acceptedCountries,
                    @NotNull @URL String comunicationUrl) {


        Assert.hasText(description, "description is required");
        Assert.notNull(paymentMethodProviderType, "paymentMethodProviderType is required");

        if (taxAmount == null || taxAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("taxAmount is required and must be grather than 0");
        }
        if (acceptedBrands == null || acceptedBrands.isEmpty()) {
            throw new IllegalArgumentException("acceptedBrands is required");
        }
        if (acceptedCountries == null || acceptedCountries.isEmpty()) {
            throw new IllegalArgumentException("acceptedCountries is required");
        }

        Assert.hasText(comunicationUrl, "comunicationUrl is required");

        this.description = description;
        this.paymentMethodProviderType = paymentMethodProviderType;
        this.taxAmount = taxAmount;
        this.acceptedBrands.addAll(acceptedBrands);
        this.acceptedCountries.addAll(acceptedCountries);
        this.comunicationUrl = comunicationUrl;
    }

    public boolean accept(CountryCode countryCode) {
        return this.acceptedCountries.stream().anyMatch(currentCountryCode -> currentCountryCode.equals(countryCode));
    }

    public boolean accept(Brand brand) {
        return this.acceptedBrands.stream().anyMatch(currentBrand -> currentBrand.equals(brand));
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public String getComunicationUrl() {
        return comunicationUrl;
    }

    public String getDescription() {
        return description;
    }
}
