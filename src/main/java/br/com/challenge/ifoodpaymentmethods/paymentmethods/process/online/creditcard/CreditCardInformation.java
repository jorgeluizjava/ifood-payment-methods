package br.com.challenge.ifoodpaymentmethods.paymentmethods.process.online.creditcard;

import br.com.challenge.ifoodpaymentmethods.converters.YearMonthDateAttributeConverter;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.YearMonth;

@Embeddable
public class CreditCardInformation {

    @NotNull
    @Enumerated(EnumType.STRING)
    private Brand brand;

    @NotBlank
    private String nameOnCard;

    @NotBlank
    @CreditCardNumber
    private String cardNumber;

    @NotBlank
    @Size(min = 3, max = 3)
    private String securityCode;

    @Column(columnDefinition = "date")
    @Convert(converter = YearMonthDateAttributeConverter.class)
    @FutureOrPresent
    private YearMonth expirationDate;

    @Deprecated
    CreditCardInformation() {}

    public CreditCardInformation(
            @NotNull Brand brand,
            @NotBlank String nameOnCard,
            @NotBlank String cardNumber,
            @NotNull @Size(min = 3, max = 3) String securityCode,
            @FutureOrPresent YearMonth expirationDate) {

        Assert.notNull(brand, "brand is required");
        Assert.hasText(nameOnCard, "nameOnCard is required");
        Assert.hasText(cardNumber, "cardNumber is required");
        Assert.isTrue(YearMonth.now().isBefore(expirationDate), "expirationDate must be valid");

        this.brand = brand;
        this.nameOnCard = nameOnCard;
        this.cardNumber = cardNumber;
        this.securityCode = securityCode;
        this.expirationDate = expirationDate;
    }

    public Brand getBrand() {
        return brand;
    }

    public String getNameOnCard() {
        return nameOnCard;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public YearMonth getExpirationDate() {
        return expirationDate;
    }
}
