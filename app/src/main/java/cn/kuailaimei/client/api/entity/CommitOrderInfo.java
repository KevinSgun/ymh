package cn.kuailaimei.client.api.entity;

import android.os.Parcel;
import android.os.Parcelable;

import cn.kuailaimei.client.api.request.RequestData;

/**
 * Created by ymh on 2016/7/4 0004.
 */
public class CommitOrderInfo implements Parcelable {

    /**
     * "amount": "80",//订单金额
     * "cId": "10003",//项目ID
     * "mId": "11902",//设计师ID
     * "mId1": "11902",//助理ID （如果该项目是套餐时需要,否则不管它可以不传）
     * "designName",//設計師名字
     * "assistantName",//助理名字
     * "name": "单剪",//项目名字
     * "sId": "10000"//店铺ID
     * "content"//消費指南，服務流程介紹
     */
    private String id;
    private float amount;
    private String sId;
    private String cId;
    private String mId;
    private String mId1;
    private String name;

    private String designName;
    private String assistantName;
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmId1() {
        return mId1;
    }

    public void setmId1(String mId1) {
        this.mId1 = mId1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignName() {
        return designName;
    }

    public void setDesignName(String designName) {
        this.designName = designName;
    }

    public String getAssistantName() {
        return assistantName;
    }

    public void setAssistantName(String assistantName) {
        this.assistantName = assistantName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeFloat(this.amount);
        dest.writeString(this.sId);
        dest.writeString(this.cId);
        dest.writeString(this.mId);
        dest.writeString(this.mId1);
        dest.writeString(this.name);
        dest.writeString(this.designName);
        dest.writeString(this.assistantName);
        dest.writeString(this.content);
    }

    public CommitOrderInfo() {
    }

    protected CommitOrderInfo(Parcel in) {
        this.id = in.readString();
        this.amount = in.readFloat();
        this.sId = in.readString();
        this.cId = in.readString();
        this.mId = in.readString();
        this.mId1 = in.readString();
        this.name = in.readString();
        this.designName = in.readString();
        this.assistantName = in.readString();
        this.content = in.readString();
    }

    public static final Parcelable.Creator<CommitOrderInfo> CREATOR = new Parcelable.Creator<CommitOrderInfo>() {
        @Override
        public CommitOrderInfo createFromParcel(Parcel source) {
            return new CommitOrderInfo(source);
        }

        @Override
        public CommitOrderInfo[] newArray(int size) {
            return new CommitOrderInfo[size];
        }
    };
}
