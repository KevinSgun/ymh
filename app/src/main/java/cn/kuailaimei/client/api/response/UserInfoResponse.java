package cn.kuailaimei.client.api.response;

/**
 * Created by ymh on 2016/7/1 0001.
 */
public class UserInfoResponse {

    /**
     * mobile : 15914087331
     * name :
     * portrait :
     * token : OdI5f+7mhAPOsw1TNmV5NVsEqANSTl9RLdAdxnCLTV31Te9dc4IZ7aMK25r98U95
     */

    private String mobile;
    private String name;
    private String portrait;
    private String token;
    private String sex;
    private String birthday;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
