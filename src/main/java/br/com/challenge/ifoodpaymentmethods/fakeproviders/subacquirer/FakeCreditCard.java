package br.com.challenge.ifoodpaymentmethods.fakeproviders.subacquirer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class FakeCreditCard {

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

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
