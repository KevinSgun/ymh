package cn.kuailaimei.client.api.request;

/**
 * Created by lu on 2016/7/2.
 */
public class Feedback implements RequestData {

    /**
     * content : 我要反馈
     * mobile : 15914087331
     */

    private String content;
    private String mobile;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
