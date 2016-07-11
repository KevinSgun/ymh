package cn.kuailaimei.client.api.request;

/**
 * Created by ymh on 2016/7/4 0004.
 */
public class CommitOrderRequest implements RequestData{
    /**
     * "amount": "80",//订单金额
     "cId": "10003",//项目ID
     "mId": "11902",//设计师ID
     "mId1": "11902",//助理ID （如果该项目是套餐时需要,否则不管它可以不传）
     "name": "单剪",//项目名字
     "payId": "1",//支付方式
     "sId": "10000"//店铺ID
     */

    private String amount;
    private String cId;
    private String mId;
    private String mId1;
    private String name;
    private String payId;
    private String sId;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCId() {
        return cId;
    }

    public void setCId(String cId) {
        this.cId = cId;
    }

    public String getMId() {
        return mId;
    }

    public void setMId(String mId) {
        this.mId = mId;
    }

    public String getMId1() {
        return mId1;
    }

    public void setMId1(String mId1) {
        this.mId1 = mId1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public String getSId() {
        return sId;
    }

    public void setSId(String sId) {
        this.sId = sId;
    }

}
