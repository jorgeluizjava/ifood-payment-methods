package br.com.challenge.ifoodpaymentmethods.paymentmethods;

public enum PaymentMethodType {

    CREDIT_CARD(true),
    DIGITAL_WALLET(true),
    CASH(false),
    CHECK(false),
    POS_MACHINE(false);

    private boolean isOnline;

    private PaymentMethodType(boolean isOnline) {
        this.isOnline = isOnline;
    }

    public boolean isOnline() {
        return isOnline;
    }
}
