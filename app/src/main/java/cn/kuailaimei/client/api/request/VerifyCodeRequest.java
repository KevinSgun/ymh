package cn.kuailaimei.client.api.request;

/**
 * Created by ymh on 2016/6/30 0030.
 */
public class VerifyCodeRequest implements RequestData{

    /**
     * apkind : register
     * mobile : 15914087331
     */
    public static final String REGISTER = "register";//注册获取验证码
    public static final String RESET_PSD = "retrievePwd";//重置密码获取验证码
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
