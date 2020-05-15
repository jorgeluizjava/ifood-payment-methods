package br.com.challenge.ifoodpaymentmethods.paymentmethods.process.creditcard;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.*;
import java.time.YearMonth;


public class CreditCardInformationDTO {

    @NotBlank(message = "nameOnBrand is required")
    private String nameOnCard;

    @NotBlank(message = "cardNumber is required")
    @CreditCardNumber(message = "cardNumber must be valid")
    private String cardNumber;

    @NotNull(message = "securityCode is required")
    @Min(value = 1, message = "securityCode must be grather than 0")
    @Max(value = 999, message = "securityCode must be less than 999")
    private Long securityCode;

    @FutureOrPresent
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM")
    private YearMonth expirationDate;

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setSecurityCode(Long securityCode) {
        this.securityCode = securityCode;
    }

    public void setExpirationDate(YearMonth expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getNameOnCard() {
        return nameOnCard;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public Long getSecurityCode() {
        return securityCode;
    }

    public YearMonth getExpirationDate() {
        return expirationDate;
    }
}
