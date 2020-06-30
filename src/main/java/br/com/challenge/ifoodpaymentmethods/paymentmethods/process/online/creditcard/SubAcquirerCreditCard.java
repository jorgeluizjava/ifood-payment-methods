package br.com.challenge.ifoodpaymentmethods.paymentmethods.process.online.creditcard;

import org.springframework.util.Assert;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SubAcquirerCreditCard {

    @NotBlank
    private String cardNumber;
    @NotBlank
    private String holder;
    @NotBlank
    private String expirationDate;
    @NotBlank
    @Size(min = 3, max = 3, message = "securityCode must have 3 characters")
    private String securityCode;
    @NotBlank
    private String brand;

    @Deprecated
    public SubAcquirerCreditCard() {}

    public SubAcquirerCreditCard(CreditCardInformation creditCardInformation) {

        Assert.notNull(creditCardInformation, "creditCardInformation is required");

        this.cardNumber = creditCardInformation.getCardNumber();
        this.holder = creditCardInformation.getNameOnCard();
        this.expirationDate = getExpirationDate(creditCardInformation);
        this.securityCode = creditCardInformation.getSecurityCode();
        this.brand = creditCardInformation.getBrand().name();
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getHolder() {
        return holder;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public String getBrand() {
        return brand;
    }

    private String getExpirationDate(CreditCardInformation creditCardInformation) {
        return creditCardInformation.getExpirationDate().getMonth() + "/" + creditCardInformation.getExpirationDate().getYear();
    }
}
