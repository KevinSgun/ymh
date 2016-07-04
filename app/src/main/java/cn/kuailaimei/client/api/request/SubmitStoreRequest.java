package cn.kuailaimei.client.api.request;

/**
 * Created by lu on 2016/7/2.
 */
public class SubmitStoreRequest implements RequestData {

    /**
     * amount : 80
     * cId : 10003
     * mId : 11902
     * mId1 : 11902
     * name : 单剪
     * payId : 1
     * sId : 10000
     */

    private String amount;
    private String cId;
    private String mId;
    private String mId1;
    private String name;
    private String payId;
    private String sId;

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
