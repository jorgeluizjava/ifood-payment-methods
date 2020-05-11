package br.com.challenge.ifoodpaymentmethods.shared.fraudster;

import br.com.challenge.ifoodpaymentmethods.user.User;

public interface FraudsterCheck {
    boolean check(User user);
}
