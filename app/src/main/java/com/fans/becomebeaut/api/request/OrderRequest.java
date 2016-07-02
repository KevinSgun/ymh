package com.fans.becomebeaut.api.request;

/**
 * Created by lu on 2016/7/2.
 */
public class OrderRequest implements RequestData {


    /**
     * payId : 1
     * orderId : 20160617070205
     */

    private String payId;
    private String orderId;

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
