package cn.kuailaimei.client.api.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class ShopInfo implements Parcelable {
    private int id;
    private String name;
    private String corp;
    private String phone;
    private String address;
    private String icon;
    private int allComment;
    private int perfectCount;
    private int goodCount;
    private int badCount;
    private int isStoreUp;
    private String latitude;
    private String longitude;
    private String start;
    private String expired;
    private String satisfactory;
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCorp() {
        return corp;
    }

    public void setCorp(String corp) {
        this.corp = corp;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getAllComment() {
        return allComment;
    }

    public void setAllComment(int allComment) {
        this.allComment = allComment;
    }

    public int getPerfectCount() {
        return perfectCount;
    }

    public void setPerfectCount(int perfectCount) {
        this.perfectCount = perfectCount;
    }

    public int getGoodCount() {
        return goodCount;
    }

    public void setGoodCount(int goodCount) {
        this.goodCount = goodCount;
    }

    public int getBadCount() {
        return badCount;
    }

    public void setBadCount(int badCount) {
        this.badCount = badCount;
    }

    public int getIsStoreUp() {
        return isStoreUp;
    }

    public void setIsStoreUp(int isStoreUp) {
        this.isStoreUp = isStoreUp;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getExpired() {
        return expired;
    }

    public void setExpired(String expired) {
        this.expired = expired;
    }

    public String getSatisfactory() {
        return satisfactory;
    }

    public void setSatisfactory(String satisfactory) {
        this.satisfactory = satisfactory;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.corp);
        dest.writeString(this.phone);
        dest.writeString(this.address);
        dest.writeString(this.icon);
        dest.writeInt(this.allComment);
        dest.writeInt(this.perfectCount);
        dest.writeInt(this.goodCount);
        dest.writeInt(this.badCount);
        dest.writeInt(this.isStoreUp);
        dest.writeString(this.latitude);
        dest.writeString(this.longitude);
        dest.writeString(this.start);
        dest.writeString(this.expired);
        dest.writeString(this.satisfactory);
    }

    public ShopInfo() {
    }

    protected ShopInfo(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.corp = in.readString();
        this.phone = in.readString();
        this.address = in.readString();
        this.icon = in.readString();
        this.allComment = in.readInt();
        this.perfectCount = in.readInt();
        this.goodCount = in.readInt();
        this.badCount = in.readInt();
        this.isStoreUp = in.readInt();
        this.latitude = in.readString();
        this.longitude = in.readString();
        this.start = in.readString();
        this.expired = in.readString();
        this.satisfactory = in.readString();
    }

    public static final Parcelable.Creator<ShopInfo> CREATOR = new Parcelable.Creator<ShopInfo>() {
        @Override
        public ShopInfo createFromParcel(Parcel source) {
            return new ShopInfo(source);
        }

        @Override
        public ShopInfo[] newArray(int size) {
            return new ShopInfo[size];
        }
    };
}