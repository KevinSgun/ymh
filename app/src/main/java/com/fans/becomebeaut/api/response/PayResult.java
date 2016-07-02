package com.fans.becomebeaut.api.response;

/**
 * Created by lu on 2016/7/2.
 */
public class PayResult {
    private String orderInfo;
    private int payType;//支付方式 1支付宝 2微信
    public String getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }
}
