package br.com.challenge.ifoodpaymentmethods.paymentmethods.providers;

import br.com.challenge.ifoodpaymentmethods.paymentmethods.process.online.creditcard.Brand;
import br.com.challenge.ifoodpaymentmethods.shared.CountryCode;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "provider_configuration")
public class ProviderConfiguration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String description;

    @CollectionTable(name = "provider_accepted_brands")
    @ElementCollection(targetClass = Brand.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Brand> acceptedBrands = new HashSet<>();

    @CollectionTable(name = "provider_accepted_countries")
    @ElementCollection(targetClass = CountryCode.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<CountryCode> acceptedCountries = new HashSet<>();

    @Deprecated
    ProviderConfiguration(){}

    public ProviderConfiguration(
                    @NotBlank String description,
                    @NotNull @NotEmpty Set<Brand> acceptedBrands,
                    @NotNull @NotEmpty Set<CountryCode> acceptedCountries) {

        Assert.hasText(description, "description is required");

        if (acceptedBrands == null || acceptedBrands.isEmpty()) {
            throw new IllegalArgumentException("acceptedBrands is required");
        }
        if (acceptedCountries == null || acceptedCountries.isEmpty()) {
            throw new IllegalArgumentException("acceptedCountries is required");
        }

        this.description = description;
        this.acceptedBrands.addAll(acceptedBrands);
        this.acceptedCountries.addAll(acceptedCountries);
    }

    public Long getId() {
        return id;
    }

    public boolean accept(CountryCode countryCode) {
        return this.acceptedCountries.stream().anyMatch(currentCountryCode -> currentCountryCode.equals(countryCode));
    }

    public boolean accept(Brand brand) {
        return this.acceptedBrands.stream().anyMatch(currentBrand -> currentBrand.equals(brand));
    }

}
