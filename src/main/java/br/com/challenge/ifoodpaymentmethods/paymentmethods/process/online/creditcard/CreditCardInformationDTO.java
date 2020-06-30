package br.com.challenge.ifoodpaymentmethods.paymentmethods.process.online.creditcard;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.YearMonth;


public class CreditCardInformationDTO {

    @NotNull(message = "creditCardBrand id required")
    private Brand brand;

    @NotBlank(message = "nameOnBrand is required")
    private String nameOnCard;

    @NotBlank(message = "cardNumber is required")
    @CreditCardNumber(message = "cardNumber must be valid")
    private String cardNumber;

    @NotBlank(message = "securityCode is required")
    @Size(min = 3, max = 3)
    private String securityCode;

    @FutureOrPresent
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM")
    private YearMonth expirationDate;

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public String getNameOnCard() {
        return nameOnCard;
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public YearMonth getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(YearMonth expirationDate) {
        this.expirationDate = expirationDate;
    }

    public CreditCardInformation toModel() {
        return new CreditCardInformation(brand, nameOnCard, cardNumber, securityCode, expirationDate);
    }
}
