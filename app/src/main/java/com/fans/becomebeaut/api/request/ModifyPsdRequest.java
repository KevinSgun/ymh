package com.fans.becomebeaut.api.request;

/**
 * Created by ymh on 2016/7/4 0004.
 */
public class ModifyPsdRequest implements RequestData{

    /**
     * original : 123456
     * password : 1234567
     */

    private String original;
    private String password;

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
