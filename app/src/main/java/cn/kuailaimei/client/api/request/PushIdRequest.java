package cn.kuailaimei.client.api.request;

/**
 * Created by ymh on 2016/7/20 0020.
 */
public class PushIdRequest implements RequestData{
    private String pushId;//上传极光推送registerId的时候用

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }
}
