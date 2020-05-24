package br.com.challenge.ifoodpaymentmethods.paymentmethods.process.online;

public class ProcessPaymentResponse {

    private boolean successed;

    public ProcessPaymentResponse(boolean successed) {
        this.successed = successed;
    }

    public boolean isSuccessed() {
        return successed;
    }
}
