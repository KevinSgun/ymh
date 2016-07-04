package cn.kuailaimei.client.api.response;

import cn.kuailaimei.client.api.entity.Order;
import cn.kuailaimei.client.api.entity.PayInfo;

/**
 * Created by lu on 2016/7/2.
 */
public class OrderPayResult {
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
