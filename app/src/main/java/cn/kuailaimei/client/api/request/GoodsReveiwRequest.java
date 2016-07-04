package cn.kuailaimei.client.api.request;

/**
 * Created by lu on 2016/7/2.
 */
public class GoodsReveiwRequest {
    /**
     * content : 我测试一下评论
     * mId : 11902
     * orderId : 1111111
     * sId : 10000
     * type : 1
     */

    private String content;
    private String mId;
    private String orderId;
    private String sId;
    private String type;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMId() {
        return mId;
    }

    public void setMId(String mId) {
        this.mId = mId;
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
