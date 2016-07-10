package cn.kuailaimei.client.api.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lu on 2016/7/2.
 */
public class StockItem implements Parcelable {

    /**
     * code : 10045:10046;10049:10053
     * gid : 1
     * id : 11195
     * inventory : 1000
     * name : 机身内存:8G;手机颜色;土豪金
     * price : 208
     */

    private String code;
    private int gid;
    private int id;
    private int inventory;
    private String name;
    private int price;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeInt(this.gid);
        dest.writeInt(this.id);
        dest.writeInt(this.inventory);
        dest.writeString(this.name);
        dest.writeInt(this.price);
    }

    public StockItem() {
    }

    protected StockItem(Parcel in) {
        this.code = in.readString();
        this.gid = in.readInt();
        this.id = in.readInt();
        this.inventory = in.readInt();
        this.name = in.readString();
        this.price = in.readInt();
    }

    public static final Parcelable.Creator<StockItem> CREATOR = new Parcelable.Creator<StockItem>() {
        @Override
        public StockItem createFromParcel(Parcel source) {
            return new StockItem(source);
        }

        @Override
        public StockItem[] newArray(int size) {
            return new StockItem[size];
        }
    };
}
