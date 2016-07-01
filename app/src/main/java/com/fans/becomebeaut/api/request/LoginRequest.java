package com.fans.becomebeaut.api.request;

/**
 * Created by ymh on 2016/7/1 0001.
 */
public class LoginRequest implements RequestData{

    /**
     * password : 123456
     * userName : 15914087331
     */

    private String password;
    private String userName;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
