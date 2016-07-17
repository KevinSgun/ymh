package cn.kuailaimei.client.api.request;

/**
 * Created by lu on 2016/7/2.
 */
public class CreditPayRequest implements RequestData {

    private String orderId;
    private String payId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }
}
