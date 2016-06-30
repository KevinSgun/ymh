package com.zitech.framework.data.network.entity;

/**
 * Created by ymh on 2016/6/30 0030.
 */
public class Basic {

    /**
     * msg : 操作成功！
     * sign : 95b6d9b28162bf6d80f1929cbdc29934
     * status : 1
     */

    private String msg;
    private String sign;
    private int status;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
