package com.fans.becomebeaut.api.entity;

public class Employee {
    private String alias;
    private int bottomPrice;
    private int commentCount;
    private String portrait;
    private String position;
    private String satisfactory;
    private int sumScore;
    private int uid;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getBottomPrice() {
        return bottomPrice;
    }

    public void setBottomPrice(int bottomPrice) {
        this.bottomPrice = bottomPrice;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getSatisfactory() {
        return satisfactory;
    }

    public void setSatisfactory(String satisfactory) {
        this.satisfactory = satisfactory;
    }

    public int getSumScore() {
        return sumScore;
    }

    public void setSumScore(int sumScore) {
        this.sumScore = sumScore;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}