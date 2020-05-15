package br.com.challenge.ifoodpaymentmethods.paymentmethods.process.creditcard;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.util.Assert;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.YearMonth;

public class CreditCardInformation {

    @NotBlank
    private String nameOnCard;
    @NotBlank
    @CreditCardNumber
    private String cardNumber;
    @NotNull
    @Min(value = 1)
    @Max(value = 999)
    private Long securityCode;
    @FutureOrPresent
    private YearMonth expirationDate;

    @Deprecated
    CreditCardInformation() {}

    public CreditCardInformation(
            @NotBlank String nameOnCard,
            @NotBlank String cardNumber,
            @NotNull @Min(value = 1) @Max(value = 999) Long securityCode,
            @FutureOrPresent YearMonth expirationDate) {

        Assert.hasText(nameOnCard, "nameOnCard is required");
        Assert.hasText(cardNumber, "cardNumber is required");
        if (securityCode == null || securityCode < 1 || securityCode > 999) {
            throw new IllegalArgumentException("securityCode is required and must be between 1 and 999");
        }
        if (expirationDate.isBefore(YearMonth.now())) {
            throw new IllegalArgumentException("expirationDate must be valid");
        }

        this.nameOnCard = nameOnCard;
        this.cardNumber = cardNumber;
        this.securityCode = securityCode;
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return "CreditCardInformation{" +
                "nameOnCard='" + nameOnCard + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", securityCode=" + securityCode +
                ", expirationDate=" + expirationDate +
                '}';
    }
}
