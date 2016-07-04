package cn.kuailaimei.client.api.response;

import cn.kuailaimei.client.api.request.RequestData;

/**
 * Created by lu on 2016/7/2.
 */
public class VipUserHomeResposne implements RequestData {


    private int completed;
    private int id;
    private int integral;
    private String name;
    private int noPay;
    private String portrait;
    private int waitComment;

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNoPay() {
        return noPay;
    }

    public void setNoPay(int noPay) {
        this.noPay = noPay;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public int getWaitComment() {
        return waitComment;
    }

    public void setWaitComment(int waitComment) {
        this.waitComment = waitComment;
    }
}
