package com.fans.becomebeaut.api.response;

import com.fans.becomebeaut.api.entity.Order;
import com.fans.becomebeaut.api.entity.PayInfo;

/**
 * Created by lu on 2016/7/2.
 */
public class UploadCreditResponse {
    private Order order;
    private PayInfo payInfo;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public PayInfo getPayInfo() {
        return payInfo;
    }

    public void setPayInfo(PayInfo payInfo) {
        this.payInfo = payInfo;
    }
}
