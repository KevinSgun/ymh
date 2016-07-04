package cn.kuailaimei.client.api.request;

/**
 * Created by ymh on 2016/7/4 0004.
 */
public class OrderCommentRequest implements RequestData{

    /**
     "content": "我测试一下评论",
     "orderId": "1111111",//订单号
     "sId": "10000",//店铺ID
     "type": "1"//评论类型 1 非常满意 2满意 3 不满意
     */

    private String content;
    private String orderId;
    private String sId;
    private String type;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSId() {
        return sId;
    }

    public void setSId(String sId) {
        this.sId = sId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
