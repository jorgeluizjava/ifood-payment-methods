package br.com.challenge.ifoodpaymentmethods.paymentmethods.process.creditcard;

import br.com.challenge.ifoodpaymentmethods.paymentmethods.Brand;
import br.com.challenge.ifoodpaymentmethods.shared.CountryCode;
import br.com.challenge.ifoodpaymentmethods.user.User;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CreditCardPaymentRequest {

    @NotNull(message = "countryCode id required")
    private CountryCode countryCode;

    @Valid
    private CreditCardInformationDTO creditCardInformation;

    @NotNull(message = "creditCardBrand id required")
    private Brand brand;

    @Min(value = 1, message = "amount must be grather than 0")
    private BigDecimal amount;

    public void setCountryCode(CountryCode countryCode) {
        this.countryCode = countryCode;
    }

    public void setCreditCardInformation(CreditCardInformationDTO creditCardInformation) {
        this.creditCardInformation = creditCardInformation;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public CreditCardInformationDTO getCreditCardInformation() {
        return creditCardInformation;
    }

    public CountryCode getCountryCode() {
        return countryCode;
    }

    public Brand getBrand() {
        return brand;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public PaymentRequest toModel(User user) {

        CreditCardInformation creditCardInformation = new CreditCardInformation(this.creditCardInformation.getNameOnCard(),
                                                                                this.creditCardInformation.getCardNumber(),
                                                                                this.creditCardInformation.getSecurityCode(),
                                                                                this.creditCardInformation.getExpirationDate()
                                                        );


        return new PaymentRequest(user, countryCode, brand, creditCardInformation, amount);
    }
}
