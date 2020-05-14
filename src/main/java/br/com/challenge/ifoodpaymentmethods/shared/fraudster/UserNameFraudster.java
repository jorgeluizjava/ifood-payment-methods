package br.com.challenge.ifoodpaymentmethods.shared.fraudster;

import br.com.challenge.ifoodpaymentmethods.user.User;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class UserNameFraudster implements FraudsterCheck {
    @Override
    public boolean check(User user) {
        Assert.notNull(user, "User must not be null");
        return user.getName().length() < 4;
    }
}
