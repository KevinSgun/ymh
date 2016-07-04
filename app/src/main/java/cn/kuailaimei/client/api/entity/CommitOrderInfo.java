package cn.kuailaimei.client.api.entity;

import android.os.Parcel;
import android.os.Parcelable;

import cn.kuailaimei.client.api.request.RequestData;

/**
 * Created by ymh on 2016/7/4 0004.
 */
public class CommitOrderInfo implements Parcelable{

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

    private float amount;
    private String sId;
    private String cId;
    private String mId;
    private String mId1;
    private String name;

    private String designName;
    private String assistantName;
    private String content;

    protected CommitOrderInfo(Parcel in) {
        amount = in.readFloat();
        sId = in.readString();
        cId = in.readString();
        mId = in.readString();
        mId1 = in.readString();
        name = in.readString();
        designName = in.readString();
        assistantName = in.readString();
        content = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(amount);
        dest.writeString(sId);
        dest.writeString(cId);
        dest.writeString(mId);
        dest.writeString(mId1);
        dest.writeString(name);
        dest.writeString(designName);
        dest.writeString(assistantName);
        dest.writeString(content);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CommitOrderInfo> CREATOR = new Creator<CommitOrderInfo>() {
        @Override
        public CommitOrderInfo createFromParcel(Parcel in) {
            return new CommitOrderInfo(in);
        }

        @Override
        public CommitOrderInfo[] newArray(int size) {
            return new CommitOrderInfo[size];
        }
    };

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
}
