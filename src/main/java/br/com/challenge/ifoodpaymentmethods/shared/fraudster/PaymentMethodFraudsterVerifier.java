package br.com.challenge.ifoodpaymentmethods.shared.fraudster;

import br.com.challenge.ifoodpaymentmethods.paymentmethods.PaymentMethod;
import br.com.challenge.ifoodpaymentmethods.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class PaymentMethodFraudsterVerifier {

    @Autowired
    private List<FraudsterCheck> checkFraudsters;

    public boolean verify(@NotNull User user, @NotNull PaymentMethod paymentMethod) {

        Assert.notNull(user, "User must not be null");
        Assert.notNull(paymentMethod, "PaymentMethod must not be null");

        boolean isFraudster = checkFraudsters.stream().anyMatch(fraudsterCheck -> fraudsterCheck.check(user));
        if (!isFraudster) {
            return true;
        }
        if (!paymentMethod.isOnline()) {
            return true;
        }
        return false;
    }
}
