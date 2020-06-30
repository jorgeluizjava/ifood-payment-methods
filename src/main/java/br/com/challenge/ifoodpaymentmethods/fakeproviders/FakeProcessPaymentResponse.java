package br.com.challenge.ifoodpaymentmethods.fakeproviders;

public class FakeProcessPaymentResponse {

    private String orderId;
    private String status;

    public FakeProcessPaymentResponse(String orderId, String status) {
        this.orderId = orderId;
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getStatus() {
        return status;
    }
}
