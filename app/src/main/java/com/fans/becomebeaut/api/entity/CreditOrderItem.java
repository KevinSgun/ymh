package com.fans.becomebeaut.api.entity;

/**
 * Created by lu on 2016/7/2.
 */
public class CreditOrderItem {

    /**
     * cover : http://img4.imgtn.bdimggp=0.jpg
     * date : 2016-06-21 01:49:23
     * msg : 待付款
     * name : 0首付可分期Coolpad/酷派 8690-T00大神X7移
     * po : 20160621221258
     * score : 500
     * price : 0
     * status : 1
     */

    private String cover;
    private String date;
    private String msg;
    private String name;
    private String po;
    private int score;
    private int price;
    private int status;

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPo() {
        return po;
    }

    public void setPo(String po) {
        this.po = po;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
