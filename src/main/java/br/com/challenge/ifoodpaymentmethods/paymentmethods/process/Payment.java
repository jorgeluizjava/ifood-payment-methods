package br.com.challenge.ifoodpaymentmethods.paymentmethods.process;

import br.com.challenge.ifoodpaymentmethods.paymentmethods.PaymentMethod;
import br.com.challenge.ifoodpaymentmethods.paymentmethods.process.online.ProcessPayment;
import br.com.challenge.ifoodpaymentmethods.paymentmethods.process.online.ProcessPaymentResponse;
import br.com.challenge.ifoodpaymentmethods.paymentmethods.process.online.creditcard.CreditCardInformation;
import br.com.challenge.ifoodpaymentmethods.restaurant.Restaurant;
import br.com.challenge.ifoodpaymentmethods.user.User;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "orderId is required")
    private Long orderId;

    @ManyToOne
    @NotNull(message = "restaurant is required")
    private Restaurant restaurant;

    @ManyToOne
    @NotNull(message = "user is required")
    private User user;

    @ManyToOne
    @NotNull(message = "paymentMethod is required")
    private PaymentMethod paymentMethod;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Min(value = 1, message = "amount must be grather than 0")
    private BigDecimal amount;

    private LocalDateTime createdAt = LocalDateTime.now();

    @Embedded
    private CreditCardInformation creditCardInformation;

    @Deprecated
    Payment() {}

    public Payment(
            @NotNull Long orderId,
            @NotNull Restaurant restaurant,
            @NotNull User user,
            @NotNull PaymentMethod paymentMethod,
            @NotNull PaymentStatus paymentStatus,
            @Min(value = 1) BigDecimal amount) {

        Assert.notNull(orderId, "orderId is required");
        Assert.notNull(restaurant, "restaurant is required");
        Assert.notNull(user, "user is required");
        Assert.notNull(paymentStatus, "paymentStatus is required");
        Assert.notNull(paymentMethod, "paymentMethod is required");

        Assert.notNull(amount, "amount is required");
        Assert.isTrue(amount.compareTo(BigDecimal.ZERO) > 0, "");

        this.orderId = orderId;
        this.restaurant = restaurant;
        this.user = user;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.amount = amount;
    }

    public Payment(
            @NotNull Long orderId,
            @NotNull Restaurant restaurant,
            @NotNull User user,
            @NotNull PaymentMethod paymentMethod,
            @NotNull PaymentStatus paymentStatus,
            @Min(value = 1) BigDecimal amount,
            CreditCardInformation creditCardInformation) {

        this(orderId, restaurant, user, paymentMethod, paymentStatus, amount);

        Assert.notNull(creditCardInformation, "creditCardInformation is required");
        this.creditCardInformation = creditCardInformation;
    }

    public Long getId() {
        return id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public User getUser() {
        return user;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public CreditCardInformation getCreditCardInformation() {
        return creditCardInformation;
    }

    public void finished() {
        this.paymentStatus = PaymentStatus.FINISHED;
    }

    public void register(ProcessPaymentResponse processPaymentResponse) {
        Assert.notNull(processPaymentResponse, "processPaymentResponse");

        boolean isSuccessed = processPaymentResponse.isSuccessed();
        if (!isSuccessed) {
            this.paymentStatus = PaymentStatus.FAILED;
            return;
        }

        if (!isFinished()) {
            this.paymentStatus = PaymentStatus.FINISHED;
            return;
        }

        throw new IllegalStateException("payment has already been taken");
    }

    private boolean isFinished() {
        return this.paymentStatus.equals(PaymentStatus.FINISHED);
    }
}
