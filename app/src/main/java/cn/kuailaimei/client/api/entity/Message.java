package cn.kuailaimei.client.api.entity;

/**
 * Created by lu on 2016/7/2.
 */
public class Message {

    /**
     * content : ni hao a
     * id : 71
     * inTime : 2016-06-08 17:36:02
     * senderId : 10000
     */

    private String content;
    private int id;
    private String inTime;
    private int senderId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }
}
