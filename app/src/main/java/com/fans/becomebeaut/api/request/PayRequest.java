package com.fans.becomebeaut.api.request;

/**
 * Created by lu on 2016/7/2.
 */
public class PayRequest implements RequestData{


    /**
     * payType : 1
     * po : 20160617070205
     */

    private String payType;
    private String po;

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPo() {
        return po;
    }

    public void setPo(String po) {
        this.po = po;
    }
}
