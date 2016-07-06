package cn.kuailaimei.client.api.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Designer implements Parcelable {
    private int agent;
    private String alias;
    private int commentCount;
    private int id;
    private int orderCount;
    private String orderRate;
    private String portrait;
    private String position;
    private int reserveCount;
    private String satisfactory;
    private String signature;
    private int sumScore;


    public int getAgent() {
        return agent;
    }

    public void setAgent(int agent) {
        this.agent = agent;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public String getOrderRate() {
        return orderRate;
    }

    public void setOrderRate(String orderRate) {
        this.orderRate = orderRate;
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

    public int getReserveCount() {
        return reserveCount;
    }

    public void setReserveCount(int reserveCount) {
        this.reserveCount = reserveCount;
    }

    public String getSatisfactory() {
        return satisfactory;
    }

    public void setSatisfactory(String satisfactory) {
        this.satisfactory = satisfactory;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getSumScore() {
        return sumScore;
    }

    public void setSumScore(int sumScore) {
        this.sumScore = sumScore;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.agent);
        dest.writeString(this.alias);
        dest.writeInt(this.commentCount);
        dest.writeInt(this.id);
        dest.writeInt(this.orderCount);
        dest.writeString(this.orderRate);
        dest.writeString(this.portrait);
        dest.writeString(this.position);
        dest.writeInt(this.reserveCount);
        dest.writeString(this.satisfactory);
        dest.writeString(this.signature);
        dest.writeInt(this.sumScore);
    }

    public Designer() {
    }

    protected Designer(Parcel in) {
        this.agent = in.readInt();
        this.alias = in.readString();
        this.commentCount = in.readInt();
        this.id = in.readInt();
        this.orderCount = in.readInt();
        this.orderRate = in.readString();
        this.portrait = in.readString();
        this.position = in.readString();
        this.reserveCount = in.readInt();
        this.satisfactory = in.readString();
        this.signature = in.readString();
        this.sumScore = in.readInt();
    }

    public static final Parcelable.Creator<Designer> CREATOR = new Parcelable.Creator<Designer>() {
        @Override
        public Designer createFromParcel(Parcel source) {
            return new Designer(source);
        }

        @Override
        public Designer[] newArray(int size) {
            return new Designer[size];
        }
    };
}