package cn.kuailaimei.client.api.request;

/**
 * Created by ymh on 2016/7/4 0004.
 */
public class OrderIDRequest implements RequestData{
    /**
     *  "orderId": "20160617070205"//订单号
     */
   private String orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
