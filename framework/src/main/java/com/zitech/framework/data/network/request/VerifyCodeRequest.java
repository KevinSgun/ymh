package com.zitech.framework.data.network.request;

/**
 * Created by ymh on 2016/6/30 0030.
 */
public class VerifyCodeRequest implements RequestData{

    /**
     * apkind : register
     * mobile : 15914087331
     */

    private String apkind;
    private String mobile;

    public String getApkind() {
        return apkind;
    }

    public void setApkind(String apkind) {
        this.apkind = apkind;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
