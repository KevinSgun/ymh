package cn.kuailaimei.client.api.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class DesignerService implements Parcelable {
    private int isGroup;//是否组合套餐 0 不是 1是
    private String content;
    private String name;
    private String price;
    private int cid;
    private int mid;
    private int sid;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIsGroup() {
        return isGroup;
    }

    public void setIsGroup(int isGroup) {
        this.isGroup = isGroup;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.isGroup);
        dest.writeString(this.content);
        dest.writeString(this.name);
        dest.writeString(this.price);
        dest.writeInt(this.cid);
        dest.writeInt(this.mid);
        dest.writeInt(this.sid);
    }

    public DesignerService() {
    }

    protected DesignerService(Parcel in) {
        this.isGroup = in.readInt();
        this.content = in.readString();
        this.name = in.readString();
        this.price = in.readString();
        this.cid = in.readInt();
        this.mid = in.readInt();
        this.sid = in.readInt();
    }

    public static final Parcelable.Creator<DesignerService> CREATOR = new Parcelable.Creator<DesignerService>() {
        @Override
        public DesignerService createFromParcel(Parcel source) {
            return new DesignerService(source);
        }

        @Override
        public DesignerService[] newArray(int size) {
            return new DesignerService[size];
        }
    };
}