package cn.kuailaimei.client.api.entity;

/**
 *
 *  "noncestr": "4d630f9347177b17ec7a362f19489239",//随机串
 "packageValue": "Sign=WXPay",//包名
 "prepayId": "wx2016072016395678d8785e500001048418",//预支付ID
 "sign": "45DFE222AFB650E7428DA8EF879708D4",//签名
 "timestamp": 1469003996,//时间戳
 "wxAppId": "wx7d71a97d624c4e02",//微信APP_ID
 "wxNotifyUrl": "http://211.149.237.131:8080/klm/api//store/order/resultWx",//回调地址
 "wxRsaPrivate": "Xiq3OIJSODFJLqoXkio2doqWP955fqYu",//微信API密钥
 "wxSeller": "1367306002"//微信商户号
 * Created by lu on 2016/7/2.
 */
public class PayInfo {

    private String payInfo;
    private String noncestr;
    private String packageValue;
    private String prepayId;
    private String sign;
    private String timestamp;
    private String wxAppId;
    private String wxNotifyUrl;
    private String wxRsaPrivate;
    private String wxSeller;


    public String getPayInfo() {
        return payInfo;
    }

    public void setPayInfo(String payInfo) {
        this.payInfo = payInfo;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getPackageValue() {
        return packageValue;
    }

    public void setPackageValue(String packageValue) {
        this.packageValue = packageValue;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getWxAppId() {
        return wxAppId;
    }

    public void setWxAppId(String wxAppId) {
        this.wxAppId = wxAppId;
    }

    public String getWxNotifyUrl() {
        return wxNotifyUrl;
    }

    public void setWxNotifyUrl(String wxNotifyUrl) {
        this.wxNotifyUrl = wxNotifyUrl;
    }

    public String getWxRsaPrivate() {
        return wxRsaPrivate;
    }

    public void setWxRsaPrivate(String wxRsaPrivate) {
        this.wxRsaPrivate = wxRsaPrivate;
    }

    public String getWxSeller() {
        return wxSeller;
    }

    public void setWxSeller(String wxSeller) {
        this.wxSeller = wxSeller;
    }
}
