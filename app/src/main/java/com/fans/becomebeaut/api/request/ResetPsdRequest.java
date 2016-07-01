package com.fans.becomebeaut.api.request;

/**
 * Created by ymh on 2016/7/1 0001.
 */
public class ResetPsdRequest implements RequestData{

    /**
     * code : 992348
     * mobile : 15914087331
     * password : 123456
     */

    private String code;
    private String mobile;
    private String password;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
